package com.scaler.userauthenticationservice.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scaler.userauthenticationservice.utils.Convertion;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.jboss.aerogear.security.otp.api.Base32;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@JsonDeserialize
@JsonSerialize
public class User extends BaseModel{
    private static final int EXPIRATION = 60 * 24;

    private String email;
    private String password;
    private boolean isUsing2FA;
    private String secret;
    private Date expiryDate;

    public User(){
        super();
        this.secret = Base32.random();
        this.setExpiryDate(Convertion.calculateExpiryDate(EXPIRATION));
    }

    @ManyToMany( fetch = FetchType.EAGER )
    private List<Role> role = new ArrayList<>();
}
