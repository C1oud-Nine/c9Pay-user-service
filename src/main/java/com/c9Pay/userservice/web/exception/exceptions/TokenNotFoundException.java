package com.c9Pay.userservice.web.exception.exceptions;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(){
        super("Token not found exception");
    }
}
