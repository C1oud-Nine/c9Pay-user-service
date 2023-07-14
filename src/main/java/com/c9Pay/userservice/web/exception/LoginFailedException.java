package com.c9Pay.userservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(UNAUTHORIZED)
public class LoginFailedException extends RuntimeException {
    public LoginFailedException(){
        super("Login Failed");
    }
}
