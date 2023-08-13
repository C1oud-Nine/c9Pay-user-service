package com.c9Pay.userservice.web.exception.exceptions;

/**
 * 토큰을 찾을 수 없는 경우를 나타내는 예외 클래스
 */
public class TokenNotFoundException extends RuntimeException {
    /**
     * 토큰을 찾을 수 없는 예외 객체를 생성한다.
     */
    public TokenNotFoundException(){
        super("Token not found exception");
    }
}
