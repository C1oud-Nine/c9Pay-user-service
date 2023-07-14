package com.c9Pay.userservice.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class DuplicatedUserException extends RuntimeException {
    public DuplicatedUserException(String format) {
        super(format);
    }
}
