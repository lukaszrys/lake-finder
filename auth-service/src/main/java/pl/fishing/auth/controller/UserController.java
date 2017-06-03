package pl.fishing.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.auth.dto.UserDto;
import pl.fishing.auth.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        return principal;
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserDto user){
        userService.registerUser(user);
    }

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@PathVariable String id, @RequestBody UserDto user){
        userService.editUser(user, id);
    }
}
