package com.c9Pay.userservice.web.exception.exceptions;

/**
 * Gateway가 아닌 접근 시 발생하는 예외
 */
public class BadAccessException extends RuntimeException{
    /**
     * Spring cloud Gateway가 아닌 접근시 예외를 생성한다.
     */
    public BadAccessException(){
        super("Bad Access occur");
    }
}
