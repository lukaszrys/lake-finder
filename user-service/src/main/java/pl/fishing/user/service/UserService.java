package pl.fishing.user.service;

import pl.fishing.user.dto.UserAuthDto;

import java.security.Principal;

public interface UserService {
    void registerUser(UserAuthDto userDto);

    void addFriend(Principal principal, String userId);
}
