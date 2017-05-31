package pl.fishing.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fishing.commons.exception.NotFoundException;
import pl.fishing.user.dto.UserAuthDto;
import pl.fishing.user.exception.UsernameNotUniqueException;
import pl.fishing.commons.exception.ValidationException;
import pl.fishing.user.feign.AuthServiceFeign;
import pl.fishing.user.model.User;
import pl.fishing.user.repository.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.security.Principal;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AuthServiceFeign authServiceFeign;

    @Autowired
    private UserRepository userRepository;

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
    //TODO: Implement UserFriend with date, confirmed etc
    public void addFriend(Principal principal, String userId) {
        User user = userRepository.findOne(principal.getName());
        User userToAdd = userRepository.findOne(userId);
        if (userToAdd == null){
            throw new NotFoundException("Did not find username=" + userId);
        }
        user.getFriends().add(userToAdd);
        userRepository.save(user);
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

    //TODO: UserTransformer
    private User transformFromDto(UserAuthDto userDto, User user) {
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setRadius(userDto.getRadius().multiply(BigDecimal.valueOf(1000L)));
        return user;
    }
}
