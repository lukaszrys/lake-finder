package pl.fishing.lake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.lake.dto.FishDto;
import pl.fishing.lake.service.FishService;

import java.security.Principal;

@RestController("/fishes")
public class FishController {

    @Autowired
    private FishService fishService;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(value ="/fishes/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addFish(@RequestBody FishDto fish, Principal principal){
       fishService.addFish(fish, principal);
    }
}
