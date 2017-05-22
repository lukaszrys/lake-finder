package pl.fishing.lake.service;


import pl.fishing.lake.dto.UserGeoLocationDto;
import pl.fishing.lake.model.Lake;

public interface LakeService {

    Lake getLakeNearMe(UserGeoLocationDto userGeoLocation);
}
