package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

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
