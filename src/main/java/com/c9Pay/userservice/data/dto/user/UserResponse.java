package com.c9Pay.userservice.data.dto.user;

import com.c9Pay.userservice.data.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;


/**
 * 사용자 정보 응답 데이터 전송 객체를 나타내는 클래스
 *
 * @see com.c9Pay.userservice.data.entity.User
 */
@Data
@Builder
public class UserResponse {
    @NotBlank
    private String username;
    @NotBlank
    private String userId;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String serialNumber;

    /**
     * User 엔티티 객체를 UserResponse 객체로 매핑하여 반환한다.
     *
     * @param user User 엔티티 객체
     * @return 매핑된 UserResponse 객체
     */
    public static UserResponse mapping(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .username(user.getUsername())
                .serialNumber(user.getSerialNumber().toString())
                .build();
    }
}
