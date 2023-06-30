package com.c9Pay.userservice.web.controller;


import com.c9Pay.userservice.web.dto.LoginForm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginForm form, HttpServletResponse response) throws IOException {
        Cookie auth = new Cookie("Auth", "dummyCookie");
        response.addCookie(auth);
        //@TODO spring security에서 AuthenticationSuccessHandler 구현 필요
        response.sendRedirect("/user-service/user");
    }

}
