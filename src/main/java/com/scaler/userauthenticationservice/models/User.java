package com.scaler.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.*;

@Getter
@Setter
@Entity
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
        this.setExpiryDate(calculateExpiryDate(EXPIRATION));
    }

    @ManyToMany( fetch = FetchType.EAGER )
    private List<Role> role = new ArrayList<>();

    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
