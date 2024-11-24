package com.scaler.userauthenticationservice.oauth2.authentication;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.userauthenticationservice.oauth2.authorization.CustomGrantedAuthority;
import com.scaler.userauthenticationservice.oauth2.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Getter
@Setter
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(
        fieldVisibility = JsonAutoDetect.Visibility.ANY,
        getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE
)
public class CustomUserDetails implements OAuth2User, UserDetails {
    private  OAuth2User oauth2User;
    private  UserDto user;
    List<GrantedAuthority> authorities = new ArrayList<>();

    public CustomUserDetails(OAuth2User oauth2User, UserDto user) {
        this.oauth2User = oauth2User;
        this.user = user;
        for(String role : this.user.getRoles() ) {
            this.authorities.add(new CustomGrantedAuthority(role));
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oauth2User != null ? oauth2User.getAttributes() : new HashMap<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getName() {
        return this.getUsername();
    }
}
