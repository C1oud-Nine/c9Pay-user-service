package com.c9Pay.userservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String username;
    private String userId;
    private String password;
    private String email;
    private Long credit;
}
