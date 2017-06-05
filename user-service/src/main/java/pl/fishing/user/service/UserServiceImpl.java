package pl.fishing.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.fishing.commons.dto.ListResult;
import pl.fishing.commons.exception.NotFoundException;
import pl.fishing.commons.exception.ValidationException;
import pl.fishing.user.dto.UserAuthDto;
import pl.fishing.user.dto.UserDto;
import pl.fishing.user.dto.UserFriendDto;
import pl.fishing.user.exception.FriendshipAlreadyExistException;
import pl.fishing.user.exception.UsernameNotUniqueException;
import pl.fishing.user.feign.AuthServiceFeign;
import pl.fishing.user.model.User;
import pl.fishing.user.model.UserFriend;
import pl.fishing.user.repository.UserFriendRepository;
import pl.fishing.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AuthServiceFeign authServiceFeign;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFriendRepository userFriendRepository;

    @Override
    @Transactional
    public void registerUser(UserAuthDto userDto) {
        if (userDto == null){
            throw new ValidationException("UserAuthDto cannot be empty");
        }
        if (userRepository.findOne(userDto.getUsername()) != null){
            throw new UsernameNotUniqueException("Username already exists");
        }
        User user = new User();
        transformFromDto(userDto, user);
        userRepository.save(user);
        authServiceFeign.registerAccount(userDto);
    }

    @Override
    @Transactional
    public void addFriend(Principal principal, String userId) {
        User user = userRepository.findOne(principal.getName());
        User userToAdd = userRepository.findOne(userId);
        if (userToAdd == null){
            throw new NotFoundException("Did not find username=" + userId);
        }
        if (userFriendRepository.findByUserAndUserFriend(user,userToAdd).isPresent()) {
            throw new FriendshipAlreadyExistException();
        }
        UserFriend userFriend = createUserFriend(user, userToAdd);
        user.getFriends().add(userFriend);
        userFriendRepository.save(userFriend);
    }


    @Override
    @Transactional
    public void editUser(String id, UserAuthDto userDto) {
        User user = userRepository.findOne(id);
        if (user == null){
            throw new NotFoundException("Did not find username=" + id);
        }
        transformFromDto(userDto, user);
        userRepository.save(user);
        authServiceFeign.editAccount(id,userDto);
    }

    @Override
    @Transactional
    public ListResult<UserFriendDto> listMyFriends(Principal principal, Pageable pageable) {
        List<UserFriend> friends = userFriendRepository.findByUser(userRepository.findOne(principal.getName()));
        List<UserFriendDto> friendsDtos = friends.stream().map(f -> transformToDto(f)).collect(Collectors.toList());
        return new ListResult<>(0L, friendsDtos);
    }

    @Override
    @Transactional
    public UserDto findOne(String username) {
        User user = userRepository.findOne(username);
        if (user == null){
            throw new NotFoundException();
        }

        return transformToDto(user);
    }

    private UserFriend createUserFriend(User user, User userToAdd) {
        UserFriend userFriend = new UserFriend();
        userFriend.setUser(user);
        userFriend.setUserFriend(userToAdd);
        userFriend.setCreatedDate(LocalDate.now());
        return userFriend;
    }


    //TODO: UserTransformer
    private User transformFromDto(UserAuthDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRadius(userDto.getRadius());
        return user;
    }

    //TODO: UserFriend transformer
    private UserFriendDto transformToDto(UserFriend entity){
        UserFriendDto dto = new UserFriendDto();
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUser(transformToDto(entity.getUser()));
        dto.setUserFriend(transformToDto(entity.getUserFriend()));
        return dto;
    }

    private UserDto transformToDto(User entity){
        UserDto user = new UserDto();
        user.setUsername(entity.getUsername());
        user.setRadius(entity.getRadius());
        user.setEmail(entity.getEmail());
        return user;
    }

}
