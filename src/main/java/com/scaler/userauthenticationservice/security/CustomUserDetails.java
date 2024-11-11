package com.scaler.userauthenticationservice.security;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scaler.userauthenticationservice.models.Role;
import com.scaler.userauthenticationservice.models.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Getter
@Setter
@JsonDeserialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;
    List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.user = user;
        this.authorities = new ArrayList<>();
        for(Role role : user.getRole() ) {
            authorities.add(new CustomGrantedAuthority(role));
        }
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
}
