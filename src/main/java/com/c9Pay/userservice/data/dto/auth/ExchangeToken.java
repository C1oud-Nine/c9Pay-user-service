package com.c9Pay.userservice.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class ExchangeToken {
    private String content;
    private Long expiredAt;
    private String sign;
}
