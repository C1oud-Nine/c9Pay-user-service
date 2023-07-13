package com.c9Pay.userservice.web.controller;


import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.LoginForm;
import com.c9Pay.userservice.web.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginForm form,  HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
            Long id = userService.authenticate(form.getUserId(), form.getPassword());
            String token = tokenProvider.createToken(id);
            response.addCookie(new Cookie("Authorization", "Bearer+"+token));
            log.debug("token: {}", token);
            return ResponseEntity.ok("로그인 성공");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
