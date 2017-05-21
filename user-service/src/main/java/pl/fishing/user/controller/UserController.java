package pl.fishing.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.fishing.user.model.User;
import pl.fishing.user.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("#oauth2.hasScope('server')")
    @RequestMapping(path="/{name}", method = RequestMethod.GET)
    public User getByUsername(@PathVariable String name){
        return userRepository.findByUsername(name);
    }
}