package com.c9Pay.userservice.web.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(){
        super("Token not found exception");
    }
}
