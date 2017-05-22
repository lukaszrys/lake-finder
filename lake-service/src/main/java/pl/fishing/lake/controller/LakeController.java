package pl.fishing.lake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.lake.dto.UserGeoLocationDto;
import pl.fishing.lake.model.Lake;
import pl.fishing.lake.repository.LakeRepository;
import pl.fishing.lake.service.LakeService;

@RestController
public class LakeController {

    @Autowired
    private LakeRepository lakeRepository;

    @Autowired
    private LakeService lakeService;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    //TODO: return LakeDTO
    public Lake getLakeNearMe(@RequestBody UserGeoLocationDto userGeoLocation){
        return lakeService.getLakeNearMe(userGeoLocation);
    }
}
