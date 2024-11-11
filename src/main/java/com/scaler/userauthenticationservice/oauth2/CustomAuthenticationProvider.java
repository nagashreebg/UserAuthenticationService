package com.scaler.userauthenticationservice.oauth2;

import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.repositories.UserRepo;
import com.scaler.userauthenticationservice.security.CustomUserDetailsService;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;


public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private  UserRepo userRepo;

    @Autowired
    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
//        this.userRepo = userRepo;
        setUserDetailsService(userDetailsService);  // Sets UserDetailsService in superclass
        setPasswordEncoder(passwordEncoder);        // Sets PasswordEncoder in superclass
    }

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        CustomWebAuthenticationDetails customWebAuthenticationDetails = ((CustomWebAuthenticationDetails) auth.getDetails());
        String verificationCode = customWebAuthenticationDetails.getVerificationCode();

        String username = auth.getName();
        Optional<User> userOptional = userRepo.findByEmail(auth.getName());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        User user = userOptional.get();
        if (user.isUsing2FA()) {
            Totp totp = new Totp(user.getSecret());
            if (!isValidLong(verificationCode) || !totp.verify(verificationCode)) {
                throw new RuntimeException("Invalid verification code");
            }
        }

        Authentication result = super.authenticate(auth);
        return new UsernamePasswordAuthenticationToken(
                user, result.getCredentials(), result.getAuthorities());
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
