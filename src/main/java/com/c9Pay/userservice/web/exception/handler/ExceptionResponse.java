package com.c9Pay.userservice.web.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * 예외 응답 정보를 생성하는 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    /** 예외 발생 시간*/
    private Date timeStamp;
    /** 예외 발생 메세지*/
    private String message;
    /** 예외 세부 정보*/
    private String details;

    /** 필드 검증 오류 정보를 저장하는 맵 */
    private Map<String, String> field = new HashMap<>();

    /**
     * ExceptionResponse 객체를 생성한다.
     *
     * @param timeStamp 예외 발생 시간
     * @param message 예외 메세지
     * @param details 예외 세부 정보
     */
    public ExceptionResponse(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    /**
     * 필드의 검증 오류 정보를 추가한다.
     *
     * @param fieldName 검증 오류가 발생하는 필드의 이름
     * @param errorMessage 검증 오류 메세지
     */
    public void addValidation(String fieldName, String errorMessage){
        field.put(fieldName, errorMessage);
    }
}
