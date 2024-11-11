package com.scaler.userauthenticationservice.services;

import com.scaler.userauthenticationservice.exceptions.UserAlreadyExistsException;
import com.scaler.userauthenticationservice.models.Role;
import com.scaler.userauthenticationservice.models.Session;
import com.scaler.userauthenticationservice.models.SessionState;
import com.scaler.userauthenticationservice.models.User;
import com.scaler.userauthenticationservice.repositories.RoleRepo;
import com.scaler.userauthenticationservice.repositories.SessionRepo;
import com.scaler.userauthenticationservice.repositories.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;


@Service
public class AuthService implements IAuthService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    SessionRepo sessionRepo;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

//    @Autowired
//    SecretKey secretKey;

    @Autowired
    private RoleRepo roleRepo;

    private static final int EXPIRATION = 60 * 24;


    public User signUp( User user) throws UserAlreadyExistsException {
        Optional<User> userOpt = userRepo.findByEmail(user.getEmail());
        if( !userOpt.isPresent() ) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            for( Role role : user.getRole() ) {
                roleRepo.save(role);
            }
            User respuser = userRepo.save(user);

            return respuser;
        }
        throw new UserAlreadyExistsException( "User already exists." );
    }

    public Pair< User, MultiValueMap<String,String>> login(User user) {
        Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());
        if( optionalUser.isPresent() ) {
            User userFound = optionalUser.get();
            if( !bCryptPasswordEncoder.matches(user.getPassword(), userFound.getPassword()) ) {
                return null;
            }
//            String message = "{\n" +
//                    " \"email\": \"anurag@scaler.com\",\n" +
//                    " \"roles\": [\n" +
//                    " \"instructor\",\n" +
//                    " \"buddy\"\n" +
//                    " ],\n" +
//                    " \"expirationDate\": \"10thNovember2024\"\n" +
//                    "}";
// byte[] content = message.getBytes( StandardCharsets.UTF_8 );
// String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

            Map<String, Object> claims = new HashMap<>();
            claims.put( "user_id", userFound.getId() );
            claims.put( "username", user.getEmail() );
            claims.put( "roles", userFound.getRole() );
            long millis = System.currentTimeMillis();
            claims.put( "iat", millis );
            claims.put( "exp", millis + 1000000 );

           // String token = Jwts.builder().claims(claims).signWith(secretKey).compact();
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
           // header.add(HttpHeaders.SET_COOKIE, token);
            Session session = new Session();
            session.setUser( userFound );
            //session.setToken( token );
            session.setState( SessionState.ACTIVE );
            sessionRepo.save( session );

            Pair< User, MultiValueMap<String,String>> response = new Pair<>(userFound, header);
            return response;
        }
        return null;
    }

    public Pair< User, MultiValueMap<String,String>> createToken( User user )
    {
        Optional<User> optionalUser = userRepo.findByEmail(user.getEmail());
        if( optionalUser.isPresent() ) {
            User userFound = optionalUser.get();
            if( !bCryptPasswordEncoder.matches(user.getPassword(), userFound.getPassword()) ) {
                return null;
            }
            final String token = UUID.randomUUID().toString();
            MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
            header.add(HttpHeaders.SET_COOKIE, token);
            Session session = new Session();
            session.setUser( userFound );
            session.setToken( token );
            session.setState( SessionState.ACTIVE );
            sessionRepo.save( session );
            Pair< User, MultiValueMap<String,String>> response = new Pair<>(userFound, header);
            return response;
        }
        return null;
    }
    public boolean validateToken(String token, String email ) {

//        Optional<Session> sessionOpt = sessionRepo.findByToken(token);
//        if( sessionOpt.isEmpty() )
//            return false;
//        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
//        Claims claims = jwtParser.parseSignedClaims( token ).getPayload();
//        if(claims.isEmpty())
//            return false;
//        if(claims.get("username").equals(email ) ) {
//           Long expTime = (Long)claims.get("exp");
//           Long curTime = System.currentTimeMillis();
//           if( curTime > expTime)
//               return false;
//           return true;
//        }
        return false;
    }
}
