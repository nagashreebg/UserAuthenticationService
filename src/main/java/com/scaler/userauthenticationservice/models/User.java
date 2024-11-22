package com.scaler.userauthenticationservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
public class User extends BaseModel{
    private String email;
    private String password;
    private String username;
    private String provider;
    private String providerId;
    private boolean enabled;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}
