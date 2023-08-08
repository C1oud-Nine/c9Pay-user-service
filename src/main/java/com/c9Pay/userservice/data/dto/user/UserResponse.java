package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    @NotBlank
    private String username;
    @NotBlank
    private String userId;
    @NotBlank
    @Email
    private String email;
    @NotBlank
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
