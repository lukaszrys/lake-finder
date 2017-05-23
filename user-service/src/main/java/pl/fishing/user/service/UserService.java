package pl.fishing.user.service;

import pl.fishing.user.dto.UserAuthDto;

public interface UserService {
    void registerUser(UserAuthDto userDto);
}
