package pl.fishing.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.user.dto.UserDto;
import pl.fishing.user.feign.AuthServiceFeign;
import pl.fishing.user.model.User;
import pl.fishing.user.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthServiceFeign authServiceFeign;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path="/{name}", method = RequestMethod.GET)
    public User getByUsername(@PathVariable String name){
        return userRepository.findByUsername(name);
    }

    @RequestMapping(path="/register", method = RequestMethod.POST)
    public void registerUser(@RequestBody UserDto user){
        authServiceFeign.registerAccount(user);
        //TODO: move to service, add user in user-service
    }


}