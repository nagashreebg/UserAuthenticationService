package com.scaler.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private List<RoleDto> roles;
}