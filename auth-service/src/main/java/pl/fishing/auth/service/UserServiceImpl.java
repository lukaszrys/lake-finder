package pl.fishing.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.fishing.auth.dto.UserDto;
import pl.fishing.auth.model.User;
import pl.fishing.auth.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void registerUser(UserDto userDto) {
        
        User user = userRepository.findOne(userDto.getId());
        if (user != null){

        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
