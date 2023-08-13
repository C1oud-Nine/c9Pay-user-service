package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * 사용자 정보 데이터 전송 객체를 나타내는 클래스
 * @see com.c9Pay.userservice.data.entity.User
 */
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

    /**
     * UserDto 객체를 User 엔티티 객체로 변환하여 반환한다.
     *
     * @param serialNumber 사용자의 개체식별번호 (serialNumber)
     * @return 변환된 User 엔티티 객체
     */
    public User toEntity(UUID serialNumber){
        return User.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .serialNumber(serialNumber)
                .email(email).build();
    }
}
