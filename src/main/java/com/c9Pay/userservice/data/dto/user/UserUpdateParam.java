package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateParam {
    @NotBlank
    String username;
    @NotBlank
    String password;

    @Email
    @NotBlank
    String email;
    @NotBlank
    String userId;
}
