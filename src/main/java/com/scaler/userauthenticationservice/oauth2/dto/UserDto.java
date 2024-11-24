package com.scaler.userauthenticationservice.oauth2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String email;
    private List<String> roles;
    private String password;
    public UserDto(String email, String password, List<String> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}

