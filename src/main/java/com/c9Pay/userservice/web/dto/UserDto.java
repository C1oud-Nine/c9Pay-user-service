package com.c9Pay.userservice.web.dto;

import com.c9Pay.userservice.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String userId;
    private String password;
    private String email;

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .credit(0L)
                .password(password)
                .username(username)
                .email(email).build();
    }
}
