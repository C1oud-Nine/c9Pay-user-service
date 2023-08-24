package com.c9Pay.userservice.web.mvc.controller;


import com.c9Pay.userservice.constant.BearerConstant;
import com.c9Pay.userservice.constant.CookieConstant;
import com.c9Pay.userservice.data.dto.user.LoginForm;
import com.c9Pay.userservice.data.dto.user.TokenResponse;
import com.c9Pay.userservice.web.docs.LoginControllerDocs;
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
/**
 * 사용자 로그인을 처리하는 컨트롤러.
 *
 * @see com.c9Pay.userservice.constant.BearerConstant
 * @see com.c9Pay.userservice.constant.CookieConstant
 * @see com.c9Pay.userservice.data.dto.user.LoginForm
 * @see com.c9Pay.userservice.web.mvc.service.UserService
 * @see jakarta.servlet.http.Cookie
 * @see jakarta.servlet.http.HttpServletRequest
 * @see jakarta.servlet.http.HttpServletResponse
 */
@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class LoginController implements LoginControllerDocs {
    private final UserService userService;


    /**
     * 사용자 로그인을 처리한다.
     *
     * @param form 사용자 로그인 정보를 담고 있는 LoginForm 객체
     * @param response HTTP 응답 객체
     * @return 로그인 성공 시 성공 메세지를 담은 ResponseEntity 반환
     */
    @Override
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginForm form, HttpServletResponse response){
        String token = userService.authenticate(form.getUserId(), form.getPassword());
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, BEARER_PREFIX + token);
        TokenResponse tokenBody = new TokenResponse(BEARER_PREFIX + token);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok().body(tokenBody);
    }

}
