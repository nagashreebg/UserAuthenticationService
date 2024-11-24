package com.scaler.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateToken {
    private String token;
    private String email;
}
