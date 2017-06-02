package pl.fishing.statistics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.statistics.model.Fish;
import pl.fishing.statistics.model.FishTypeValue;
import pl.fishing.statistics.repository.FishRepository;
import pl.fishing.statistics.service.LakeStatisticsService;

import java.util.Date;
import java.util.List;

@RestController
public class StatisticController {

    @Autowired
    private FishRepository fishRepository;

    @Autowired
    private LakeStatisticsService lakeStatisticsService;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value ="/fishes/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addFish(@RequestBody Fish fish){
        fish.setUploadDate(new Date());
        fishRepository.save(fish);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(value ="/fishes/{lakeId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<FishTypeValue> addFish(@PathVariable String lakeId,
                                       @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date from,
                                       @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date to){
        return lakeStatisticsService.getStatsForLake(lakeId, from, to);
    }

}
