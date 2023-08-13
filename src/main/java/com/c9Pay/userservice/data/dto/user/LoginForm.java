package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 로그인 폼 데이터를 나타내는 클래스
 * @see jakarta.validation.constraints.NotBlank
 */
@Data @AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

    @NotBlank
    private String userId;
    @NotBlank
    private String password;
}
