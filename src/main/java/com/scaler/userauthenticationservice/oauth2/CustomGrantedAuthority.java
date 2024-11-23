package com.scaler.userauthenticationservice.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
@Getter
@Setter
@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;

    public CustomGrantedAuthority( String role ) {
        this.authority = role;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
