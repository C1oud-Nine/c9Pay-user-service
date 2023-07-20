package com.c9Pay.userservice.web.controller;


import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.user.LoginForm;
import com.c9Pay.userservice.web.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginForm form,  HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long id = userService.authenticate(form.getUserId(), form.getPassword());
        Authentication authentication = new UsernamePasswordAuthenticationToken(id, "");
        String token = tokenProvider.createToken(authentication);
        response.addCookie(new Cookie(AUTHORIZATION_HEADER, "Bearer+"+token));
        log.debug("token: {}", token);
        return ResponseEntity.ok("로그인 성공");

    }
}
