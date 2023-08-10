package com.c9Pay.userservice.web.exception.exceptions;

public class TokenGenerationFailureException extends RuntimeException {
    public TokenGenerationFailureException(){
        super("Token generation failure(user-service)");
    }
}
