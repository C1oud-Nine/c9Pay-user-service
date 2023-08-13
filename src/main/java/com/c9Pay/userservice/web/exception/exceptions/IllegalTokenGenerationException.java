package com.c9Pay.userservice.web.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 토큰 생성 예외를 나타내는 예외 클래스
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalTokenGenerationException extends RuntimeException {

    /**
     * 새로운 토큰을 생성할 수 없을 때 예외 객체를 생성한다.
     */
    public IllegalTokenGenerationException(){
        super("can not generate new Token");
    }
}
