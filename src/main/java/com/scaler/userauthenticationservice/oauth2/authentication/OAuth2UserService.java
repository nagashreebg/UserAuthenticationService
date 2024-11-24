package com.scaler.userauthenticationservice.oauth2.authentication;

import com.scaler.userauthenticationservice.exceptions.UserNameNotFoundException;
import com.scaler.userauthenticationservice.models.Role;
import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.oauth2.dto.UserDto;
import com.scaler.userauthenticationservice.oauth2.repositories.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OAuth2UserService implements UserDetailsManager {
	@Autowired
	private UserRepo userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User findOrCreateUser(String provider, String providerId, String email, OAuth2User oauth2User) {
		Optional<User> existingUser = userRepository.findByProviderAndProviderId(provider, providerId);
		if (existingUser.isPresent()) {
			return updateExistingUser(existingUser.get(), oauth2User);
		}
		return createNewUser(provider, providerId, email, oauth2User);
	}

	private User createNewUser(String provider, String providerId, String email, OAuth2User oauth2User) {
		User user = new User();
		user.setProvider(provider);
		user.setProviderId(providerId);
		user.setEmail(email);
		user.setUsername(email);
		user.setEnabled(true);

		// Set additional fields from OAuth2User attributes
		user.setName(oauth2User.getAttribute("name"));

		// Assign default roles
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("ROLE_USER"));
		user.setRoles(roles);

		return userRepository.save(user);
	}

	private User updateExistingUser(User user, OAuth2User oauth2User) {
		// Update any fields that might have changed
		user.setName(oauth2User.getAttribute("name"));
		return userRepository.save(user);
	}

	@Override
	public void createUser(UserDetails user) {
		throw new UnsupportedOperationException("User creation is handled through OAuth2 provider");
	}

	@Override
	public void deleteUser(String username) {
		User user = null;
		try {
			user = userRepository.findByEmail(username)
					.orElseThrow(() -> new UserNameNotFoundException("User not found"));
		} catch (UserNameNotFoundException e) {
			throw new RuntimeException(e);
		}
		user.setEnabled(false);
		userRepository.save(user);
	}

	@Override
	public void updateUser(UserDetails user) {
		User existingUser = null;
		try {
			existingUser = userRepository.findByUsername(user.getUsername())
					.orElseThrow(() -> new UserNameNotFoundException("User not found"));
		} catch (UserNameNotFoundException e) {
			throw new RuntimeException(e);
		}
		userRepository.save(existingUser);
	}

	@Override
	public boolean userExists(String username) {
		return userRepository.findByUsername(username).isPresent();
	}

	@Override
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		if (currentUser == null) {
			throw new AccessDeniedException("No authentication found in context");
		}
		// Get username from the authentication object
		String username = currentUser.getName();
		User user = userRepository.findByEmail( username ) .orElseThrow(() -> null);
		if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
			throw new BadCredentialsException("Invalid old password");
		}
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> null );
		UserDto userDto = new UserDto();
		userDto.setEmail( user.getEmail() );
		userDto.setPassword( user.getPassword());
		userDto.setRoles( user.getRoles().stream()
				.map(Role::getRoleName)
				.collect(Collectors.toList()));
		return new CustomUserDetails(null, userDto); // null OAuth2User as this is direct lookup
	}
}
