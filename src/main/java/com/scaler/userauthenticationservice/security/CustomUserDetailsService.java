package com.scaler.userauthenticationservice.security;

import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepo.findByEmail( username );
        if( userOptional.isEmpty() )
            return null;
        User user = userOptional.get();
        return new CustomUserDetails( user );
    }
}
