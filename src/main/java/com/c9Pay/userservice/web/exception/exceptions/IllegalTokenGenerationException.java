package com.c9Pay.userservice.web.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalTokenGenerationException extends RuntimeException {

    public IllegalTokenGenerationException(){
        super("can not generate new Token");
    }
}
