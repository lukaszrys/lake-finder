package pl.fishing.user.service;

import org.springframework.data.domain.Pageable;
import pl.fishing.commons.dto.ListResult;
import pl.fishing.user.dto.UserAuthDto;
import pl.fishing.user.dto.UserDto;
import pl.fishing.user.dto.UserFriendDto;

import java.security.Principal;

public interface UserService {
    void registerUser(UserAuthDto userDto);

    void addFriend(Principal principal, String userId);

    void editUser(String id, UserAuthDto user);

    ListResult<UserFriendDto> listMyFriends(Principal principal, Pageable pageable);

    UserDto findOne(String username);
}
