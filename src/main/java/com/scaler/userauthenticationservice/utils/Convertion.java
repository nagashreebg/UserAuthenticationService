package com.scaler.userauthenticationservice.utils;

import com.scaler.userauthenticationservice.dtos.LoginRequestDto;
import com.scaler.userauthenticationservice.dtos.RoleDto;
import com.scaler.userauthenticationservice.dtos.SignupRequestDto;
import com.scaler.userauthenticationservice.dtos.UserDto;
import com.scaler.userauthenticationservice.models.Role;
import com.scaler.userauthenticationservice.models.User;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Convertion {

    public static UserDto getUserDto( User user ) {
        UserDto userDto = new UserDto();
        userDto.setEmail( user.getEmail());
        if( user.getRole().size() > 0 )
        {
            Set<RoleDto> roles = userDto.getRoles();
            for( Role role : user.getRole() )
            {
                RoleDto roleDto = new RoleDto();
                roleDto.setRole( role.getRoleName());
                roles.add(roleDto);
            }
        }

        return userDto;
    }

    public static User getUser( SignupRequestDto signupRequestDto ) {
        User user = new User();
        user.setEmail( signupRequestDto.getEmail());
        user.setPassword(signupRequestDto.getPassword());
        for( RoleDto role : signupRequestDto.getRoles() )
        {
            user.getRole().add( new Role( role.getRole()));
        }
        return user;
    }

    public static User getUser( LoginRequestDto loginRequestDto ) {
        User user = new User();
        user.setEmail( loginRequestDto.getEmail());
        user.setPassword(loginRequestDto.getPassword());
        return user;
    }

    public static Date calculateExpiryDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
