package com.scaler.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@Entity
public class Session extends BaseModel{
    @ManyToOne
    private User user;
    private String token;

    @Enumerated( EnumType.ORDINAL )
    private SessionState state;
}
