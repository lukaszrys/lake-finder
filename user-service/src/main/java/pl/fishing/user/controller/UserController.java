package pl.fishing.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.fishing.commons.dto.ListResult;
import pl.fishing.user.dto.UserAuthDto;
import pl.fishing.user.model.User;
import pl.fishing.user.model.UserFriend;
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
    @RequestMapping(path="/find/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getByUsername(@PathVariable String name){
        return userRepository.findOne(name);
    }

    @RequestMapping(path="/user/register", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody UserAuthDto user){
        userService.registerUser(user);
    }

    @RequestMapping(path="/user/{id}", method = RequestMethod.PUT) //PUT because Alamofire fires PATCH as GET, feel free to change
    @ResponseStatus(HttpStatus.OK)
    public void editUser(@PathVariable String id, @RequestBody UserAuthDto user){
        userService.editUser(id, user);
    }

    @RequestMapping(path="/current", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getCurrentUser(Principal principal){
        return userRepository.findOne(principal.getName());
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path="/friends/{userId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addFriend(@PathVariable String userId, Principal principal){
        userService.addFriend(principal, userId);
    }

    @PreAuthorize("#oauth2.hasScope('ui')")
    @RequestMapping(path="/friends/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ListResult<UserFriend> addFriend(Principal principal, Pageable pageable){
        return userService.listMyFriends(principal, pageable);
    }


}