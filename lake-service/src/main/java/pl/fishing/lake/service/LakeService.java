package pl.fishing.lake.service;


import pl.fishing.lake.dto.UserGeoLocationDto;
import pl.fishing.lake.model.Lake;

import java.security.Principal;

public interface LakeService {

    Lake getLakeNearMe(UserGeoLocationDto userGeoLocation, Principal principal);

    Lake getLakeNearMe(UserGeoLocationDto userGeoLocation, double radius);
}
