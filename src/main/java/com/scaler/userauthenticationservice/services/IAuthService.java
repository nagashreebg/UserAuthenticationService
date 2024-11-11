package com.scaler.userauthenticationservice.services;

import com.scaler.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthenticationservice.models.User;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface IAuthService {
    User signUp(User user) throws UserAlreadyExistsException;
    //Pair< User, MultiValueMap<String,String>> login(User user);
    boolean validateToken(String token, String email );

    Pair<User, MultiValueMap<String, String>> createToken(User user);
}
