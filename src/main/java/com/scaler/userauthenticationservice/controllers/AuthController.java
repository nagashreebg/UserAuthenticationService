package com.scaler.userauthenticationservice.controllers;

import com.scaler.userauthenticationservice.dtos.*;
import com.scaler.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.services.IAuthService;
import com.scaler.userauthenticationservice.utils.Convertion;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    //Signup
    @PostMapping("signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) throws UserAlreadyExistsException {
        User user = authService.signUp(Convertion.getUser(signupRequestDto));
        if( user != null ) {
            return new ResponseEntity<>( Convertion.getUserDto( user), HttpStatus.OK );
        }
        return new ResponseEntity<>( null, HttpStatus.BAD_REQUEST );
    }
    //Login
    @PostMapping("login")
    public ResponseEntity<UserDto> login( @RequestBody LoginRequestDto loginRequestDto ) {
        User user = Convertion.getUser( loginRequestDto );
        Pair<User, MultiValueMap<String, String>> response = authService.createToken( user );
        if( response.a != null ) {
            return new ResponseEntity<>(Convertion.getUserDto( response.a ),
                                        response.b,
                                        HttpStatus.OK );
        }
        throw new RuntimeException( "Bad credentials");
    }

    @PostMapping("validateToken")
    public ResponseEntity<Boolean> validateToken( @RequestBody ValidateTokenDto validateTokenDto )
    {
        Boolean response = authService.validateToken(
                                    validateTokenDto.getToken(),
                                    validateTokenDto.getEmail()
                                    );
        if( response )
            return new ResponseEntity<>( true, HttpStatus.OK );

        return new ResponseEntity<>( false, HttpStatus.BAD_REQUEST );
    }
    //Forgot password
    @PostMapping("forgotpassword")
    public UserDto forgotPassword( @RequestBody ForgotPasswordDto forgotPasswordDto ) {
        return null;
    }

    //logout
    @PostMapping("logout")
    public boolean logout()
    {
        return true;
    }
}
