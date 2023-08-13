package com.c9Pay.userservice.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 인증서비스에서 받아올 사용자에게 응답될 토큰 정보를 담는 클래스
 *
 * @see com.c9Pay.userservice.web.client.AuthClient
 */
@Data@AllArgsConstructor@NoArgsConstructor
public class ExchangeToken {
    private String content;
    private Long expiredAt;
    private String sign;
}
