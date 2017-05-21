package pl.fishing.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
    public void registerUser(@RequestBody UserDto user){
        userService.registerUser(user);
    }
}
