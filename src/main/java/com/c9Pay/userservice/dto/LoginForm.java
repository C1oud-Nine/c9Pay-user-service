package com.c9Pay.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LoginForm {
    private String userId;
    private String password;
}
