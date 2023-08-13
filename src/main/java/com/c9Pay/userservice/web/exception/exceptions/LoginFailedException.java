package com.c9Pay.userservice.web.exception.exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
/**
 * 로그인 실패를 나타내는 예외 클래스
 */
@ResponseStatus(UNAUTHORIZED)
public class LoginFailedException extends RuntimeException {
    /**
     * 로그인 실패 예외 객체를 생성한다.
     */
    public LoginFailedException(){
        super("Login Failed");
    }
}
