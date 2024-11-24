package com.scaler.userauthenticationservice.oauth2.authentication;

import com.scaler.userauthenticationservice.models.Role;
import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.oauth2.dto.UserDto;
import com.scaler.userauthenticationservice.oauth2.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByEmail( username );
        if( userOptional.isEmpty() )
            throw new UsernameNotFoundException("User not found");
        User user = userOptional.get();
        List<String> roleNames = user.getRoles().stream()
                .map(Role::getRoleName)
                .collect(Collectors.toList());
        return new CustomUserDetails( null, new UserDto(user.getEmail(), user.getPassword(),
                                      roleNames) );
    }
}
