package com.scaler.userauthenticationservice.services;

import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.repositories.SessionRepo;
import com.scaler.userauthenticationservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Optional;

@Service
public class OAuthService implements IOAuthService {
    private static final Object APP_NAME = "UserAuthenticationService" ;
    @Autowired
    private UserRepo userRepo;

    @Override
    public User updateUser2FA(boolean use2FA) {
        Authentication curAuth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) curAuth.getPrincipal();
        currentUser.setUsing2FA(use2FA);
        currentUser = userRepo.save(currentUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                currentUser, currentUser.getPassword(), curAuth.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return currentUser;
    }

    @Override
    public String generateQRUrl(User user) throws UnsupportedEncodingException {
        return QR_PREFIX + URLEncoder.encode(String.format(
                        "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                        APP_NAME, user.getEmail(), user.getSecret(), APP_NAME),
                "UTF-8");
    }

    @Override
    public boolean validateVerificationToken(String verificationToken) {
        Optional<User> userOpt = userRepo.findBySecret(verificationToken);
        if( userOpt.isEmpty() )
            return false;
        final Calendar cal = Calendar.getInstance();
        if ((userOpt.get().getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            userOpt.get().setSecret(null);
            userRepo.save(userOpt.get());
            return false;
        }
        return true;
    }

    @Override
    public User getUser(String token) {
        Optional<User> userOptional = userRepo.findBySecret(token);
        if( userOptional.isEmpty() )
            return null;
        return userOptional.get();
    }
}
