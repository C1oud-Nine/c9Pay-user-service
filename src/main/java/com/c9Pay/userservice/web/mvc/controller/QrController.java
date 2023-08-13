package com.c9Pay.userservice.web.mvc.controller;


import com.c9Pay.userservice.data.dto.auth.ExchangeToken;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.web.client.AuthClient;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

/**
 * QR 정보 처리 관리하는 컨트롤러
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class QrController {

    private final AuthClient authClient;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    /**
     * 인증 토큰을 사용하여 QR 코드를 생성한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @param request HTTP 요청 객체
     * @return QR 코드 생성 요청 결과를 포함하는 ResponseEntity 반환
     */
    @GetMapping("/api/qr")
    public ResponseEntity<?> createQr(@CookieValue(AUTHORIZATION_HEADER) String token,
                                      HttpServletRequest request){
        String id = jwtTokenUtil.extractId(token.substring(7));
        User findUser = userService.findById(Long.valueOf(id));
        return authClient.createQR(findUser.getSerialNumber().toString());
    }
}
