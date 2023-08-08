package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
