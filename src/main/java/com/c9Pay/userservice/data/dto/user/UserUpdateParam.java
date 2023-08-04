package com.c9Pay.userservice.data.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateParam {
    String username;
    String password;
    String email;
    String userId;
}
