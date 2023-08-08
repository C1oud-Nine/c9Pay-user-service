package com.c9Pay.userservice.web.mvc.controller;


import com.c9Pay.userservice.constant.BearerConstant;
import com.c9Pay.userservice.constant.CookieConstant;
import com.c9Pay.userservice.data.dto.user.LoginForm;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginForm form, HttpServletResponse response){
        String token = userService.authenticate(form.getUserId(), form.getPassword());
        response.addCookie(new Cookie(AUTHORIZATION_HEADER, BEARER_PREFIX + token));
        return ResponseEntity.ok("로그인 성공");
    }


}
