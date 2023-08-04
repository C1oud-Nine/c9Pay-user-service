package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private String username;
    private String userId;
    private String email;

    private String serialNumber;

    public static UserResponse mapping(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .serialNumber(user.getSerialNumber().toString())
                .build();
    }
}
