package pl.fishing.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.statistics.model.Fish;
import pl.fishing.statistics.repository.FishRepository;

import java.util.Date;

@RestController
public class StatisticController {

    @Autowired
    private FishRepository fishRepository;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value ="/fishes/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addFish(@RequestBody Fish fish){
        fish.setUploadDate(new Date());
        fishRepository.save(fish);
    }

}
