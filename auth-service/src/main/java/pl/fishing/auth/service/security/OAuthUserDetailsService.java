package pl.fishing.auth.service.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.fishing.auth.service.security.dto.UserDto;

@Service
public class OAuthUserDetailsService implements UserDetailsService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDto user = null; //get user from user-service

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return user;
	}
}
