package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class UserDto {

    @NotBlank
    private String username;

    @NotBlank
    private String userId;

    @NotBlank
    private String password;

    @NotBlank
    @Email
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
