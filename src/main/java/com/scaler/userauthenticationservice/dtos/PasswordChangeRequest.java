package com.scaler.userauthenticationservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordChangeRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
