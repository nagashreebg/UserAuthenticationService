package com.scaler.userauthenticationservice.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}