package com.c9Pay.userservice.web.dto.user;

import com.c9Pay.userservice.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Data
@Builder
public class UserDto {
    private String username;
    private String userId;
    private String password;
    private String email;

    public User toEntity(UUID serialNumber){
        return User.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .serialNumber(serialNumber)
                .email(email).build();
    }
}
