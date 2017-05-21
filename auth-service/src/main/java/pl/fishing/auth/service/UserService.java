package pl.fishing.auth.service;

import pl.fishing.auth.dto.UserDto;

public interface UserService {
    void registerUser(UserDto userDto);
}
