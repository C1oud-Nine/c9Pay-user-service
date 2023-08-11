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

@Slf4j
@RestController
@RequiredArgsConstructor
public class QrController {

    private final AuthClient authClient;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @GetMapping("/api/qr")
    public ResponseEntity<?> createQr(@CookieValue(AUTHORIZATION_HEADER) String token,
                                      HttpServletRequest request){
        String id = jwtTokenUtil.extractId(token.substring(7));
        User findUser = userService.findById(Long.valueOf(id));
        return authClient.createQR(findUser.getSerialNumber().toString());
    }
}
