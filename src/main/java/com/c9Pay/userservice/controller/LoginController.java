package com.c9Pay.userservice.controller;


import com.c9Pay.userservice.dto.LoginForm;
import com.c9Pay.userservice.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginForm form, HttpServletResponse response) throws IOException {
        Cookie auth = new Cookie("Auth", "dummyCookie");
        response.addCookie(auth);
        response.sendRedirect("/user-service/user");
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto form){
        //@TODO: feign Client(auth-service: 개체 식별번호 생성 요청, credit-service: 계좌 생성 요청)
        return ResponseEntity.ok().build();
    }

}
