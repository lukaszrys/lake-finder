package pl.fishing.auth.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.fishing.auth.dto.AuthUser;
import pl.fishing.auth.model.User;
import pl.fishing.auth.repository.UserRepository;

@Service
public class OAuthUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findOne(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		AuthUser authUser = new AuthUser(user);

		return authUser;
	}
}
