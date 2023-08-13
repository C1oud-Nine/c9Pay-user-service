package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보 업데이트를 위한 요청 데이터 전송 객체를 나타내는 클래스
 */
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
