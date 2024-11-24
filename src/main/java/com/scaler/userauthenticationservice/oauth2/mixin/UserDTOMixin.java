package com.scaler.userauthenticationservice.oauth2.mixin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public abstract class UserDTOMixin {
    @JsonCreator
    public UserDTOMixin(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("roles") List<String> roles
    ) {}
}
