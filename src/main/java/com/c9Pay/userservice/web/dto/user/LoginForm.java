package com.c9Pay.userservice.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class LoginForm {
    private String userId;
    private String password;
}
