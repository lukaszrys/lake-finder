package pl.fishing.lake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.geo.Circle;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.fishing.lake.google.response.GoogleApiLake;
import pl.fishing.lake.google.response.GoogleMapsResponse;
import pl.fishing.lake.dto.UserDto;
import pl.fishing.lake.dto.UserGeoLocationDto;
import pl.fishing.lake.feign.UserServiceFeign;
import pl.fishing.lake.model.Lake;
import pl.fishing.lake.repository.LakeRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

@Service
public class LakeServiceImpl implements LakeService {

    @Value("${google.maps.key}")
    private String googleMapsApi;

    @Value("${google.maps.uri}")
    private String googleMapsUri;

    @Value("${google.static.uri}")
    private String googleMapsStatic;

    @Autowired
    private LakeRepository lakeRepository;

    @Autowired
    private UserServiceFeign userService;

    @Override
    public Lake getLakeNearMe(UserGeoLocationDto userGeoLocation, Principal principal) {
        UserDto currentUser = userService.getByUsername(principal.getName());
        return getLakeNearMe(userGeoLocation, currentUser.getRadius().doubleValue());
    }

    @Override
    public Lake getLakeNearMe(UserGeoLocationDto userGeoLocation, double radius) {
        Lake lake = getLakeFromDB(userGeoLocation, converMetersToDegrees());
        if (lake == null){
            GoogleApiLake googleApiLake = getLakeFromGoogleMaps(userGeoLocation, radius);
            if (googleApiLake == null || lakeRepository.findOne(googleApiLake.getId()) != null) {
                return null;
            }
            return saveLake(googleApiLake);
        }
        return lake;
    }

    private int converMetersToDegrees() {
        return 1;
    }

    @Override
    public Lake addLake(Lake lake, Principal principal) {
        UserGeoLocationDto geoLocationDto = new UserGeoLocationDto();
        geoLocationDto.setLatitude(BigDecimal.valueOf(lake.getPosition()[0]));
        geoLocationDto.setLongitude(BigDecimal.valueOf(lake.getPosition()[1]));
        if (getLakeNearMe(geoLocationDto, 500) != null){
            return null; //TODO: throw ex
        }
        try {
            if (validateLake(geoLocationDto.getLatitude(), geoLocationDto.getLongitude())){
                lakeRepository.save(lake);
                return lake;
            }
        }  catch (IOException | InterruptedException e) {

        }
        return null;
    }

    private GoogleApiLake getLakeFromGoogleMaps(UserGeoLocationDto userGeoLocation,double radius) {
        radius = radius > 5000 ? 5000 : radius;
        HttpEntity<GoogleMapsResponse> response = getResponseFromGoogleMaps(userGeoLocation, radius);

        GoogleMapsResponse mapsBody = response.getBody();
        if (mapsBody != null && mapsBody.getResults() != null && mapsBody.getResults().size() > 0){
            try {
                return getFirstValidLake(mapsBody.getResults().iterator());
            } catch (IOException | InterruptedException e) {
                return null; //TODO: handle exception
            }

        }
        return null; // TODO: handle
    }

    private HttpEntity<GoogleMapsResponse> getResponseFromGoogleMaps(UserGeoLocationDto userGeoLocation, double radius) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(googleMapsUri);
        builder.queryParam("location", userGeoLocation.getLatitude() + "," + userGeoLocation.getLongitude());
        builder.queryParam("radius", radius);
        builder.queryParam("type","natural_feature");
        builder.queryParam("key", googleMapsApi);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, GoogleMapsResponse.class);
    }

    private Lake saveLake(GoogleApiLake googleApiLake) {
        if (googleApiLake == null){
            return null; //TODO:
        }
        Lake lake = new Lake();
        double[] position = new double[2];
        position[0] = googleApiLake.getGeometry().getLocation().getLat().doubleValue();
        position[1] = googleApiLake.getGeometry().getLocation().getLng().doubleValue();
        lake.setName(googleApiLake.getName());
        lake.setId(googleApiLake.getId());
        lake.setPosition(position);
        lakeRepository.save(lake);
        return lake;
    }

    private GoogleApiLake getFirstValidLake(Iterator<GoogleApiLake> results) throws IOException, InterruptedException {
        if (!results.hasNext()){
            return null;
        }
        GoogleApiLake googleApiLake = results.next();
        if (validateLake(googleApiLake)) return googleApiLake;

        return getFirstValidLake(results);
    }

    private boolean validateLake(GoogleApiLake googleApiLake) throws IOException, InterruptedException {
        return validateLake(googleApiLake.getGeometry().getLocation().getLat(), googleApiLake.getGeometry().getLocation().getLng());
    }

    private boolean validateLake(BigDecimal lat, BigDecimal lng) throws IOException, InterruptedException {
        Image img = getImageFromGoogleMaps(lat, lng);
        int w = img.getWidth(null);
        int h = img.getHeight(null);
        int[] pixels = new int[w * h];
        PixelGrabber pg = new PixelGrabber(img, 0, 0, w, h, pixels, 0, w);
        pg.grabPixels();
        for (int pixel: pixels) {
            Color color = new Color(pixel);
            if (color != null && color.getRed() == 163 && color.getGreen() == 203 && color.getBlue() == 255){
                return true;
            }
        }
        return false;
    }

    private Image getImageFromGoogleMaps(BigDecimal lat, BigDecimal lng) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(googleMapsStatic);
        builder.queryParam("center", lat + "," + lng);
        builder.queryParam("zoom", 1000); //TODO: ?
        builder.queryParam("size","1x1");
        builder.queryParam("maptype", "roadmap");
        builder.queryParam("sensor", "false");
        URL url = new URL(builder.build().encode().toUri().toString());
        return ImageIO.read(url);
    }

    private Lake getLakeFromDB(UserGeoLocationDto userGeoLocation, double radius) {
        List<Lake> lakes = lakeRepository.findByPositionWithin(new Circle(userGeoLocation.getLatitude().doubleValue(), userGeoLocation.getLongitude().doubleValue(), radius));
        if (lakes.size() > 0){
            return lakes.get(0);
        }
        return null;
    }
}
