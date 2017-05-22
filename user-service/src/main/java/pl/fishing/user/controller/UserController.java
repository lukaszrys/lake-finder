package pl.fishing.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.user.dto.UserDto;
import pl.fishing.user.model.User;
import pl.fishing.user.repository.UserRepository;
import pl.fishing.user.service.UserService;

import java.security.Principal;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path="/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getByUsername(@PathVariable String name){
        return userRepository.findOne(name);
    }

    @RequestMapping(path="/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user){
        userService.registerUser(user);
    }

    @RequestMapping(path="/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser(Principal principal){
        return userRepository.findOne(principal.getName());
    }


}