package pl.fishing.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fishing.user.dto.UserDto;
import pl.fishing.user.exception.UsernameNotUniqueException;
import pl.fishing.user.exception.ValidationException;
import pl.fishing.user.feign.AuthServiceFeign;
import pl.fishing.user.model.User;
import pl.fishing.user.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private AuthServiceFeign authServiceFeign;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void registerUser(UserDto userDto) {
        if (userDto == null){
            throw new ValidationException("UserDto cannot be empty");
        }
        if (userRepository.findOne(userDto.getUsername()) != null){
            throw new UsernameNotUniqueException("Username already exists");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        authServiceFeign.registerAccount(userDto);
    }
}
