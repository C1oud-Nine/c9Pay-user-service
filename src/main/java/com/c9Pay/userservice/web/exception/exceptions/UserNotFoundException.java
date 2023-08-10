package com.c9Pay.userservice.web.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String format) {
        super(format);
    }
}
