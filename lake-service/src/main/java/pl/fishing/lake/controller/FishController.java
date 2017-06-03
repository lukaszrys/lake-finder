package pl.fishing.lake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.lake.dto.FishDto;
import pl.fishing.commons.dto.ListResult;
import pl.fishing.lake.model.Fish;
import pl.fishing.lake.model.FishType;
import pl.fishing.lake.repository.FishTypeRepository;
import pl.fishing.lake.service.FishService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class FishController {

    @Autowired
    private FishService fishService;

    @Autowired
    private FishTypeRepository fishTypeRepository;

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(value ="/fishes/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addFish(@RequestBody FishDto fish, Principal principal){
       fishService.addFish(fish, principal);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(value ="/fishes/{username}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ListResult<Fish> listFish(@PathVariable String username, Principal principal, Pageable pageable){
        return fishService.listFish(username, principal, pageable);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(value ="/fishes/types", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<FishType> listTypes(){
        Iterator<FishType> types = fishTypeRepository.findAll().iterator();
        List<FishType> list = new ArrayList<>();
        types.forEachRemaining(list::add);
        return list;
    }

}
