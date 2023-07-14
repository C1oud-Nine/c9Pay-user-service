package com.c9Pay.userservice.web.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class IllegalTokenDetailException extends RuntimeException {
    public IllegalTokenDetailException() {
        super("Invalid token info.");
    }
}
