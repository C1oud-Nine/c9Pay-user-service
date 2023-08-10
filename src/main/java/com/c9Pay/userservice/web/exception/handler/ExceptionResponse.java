package com.c9Pay.userservice.web.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private Date timeStamp;
    private String message;
    private String details;

    private Map<String, String> field = new HashMap<>();

    public ExceptionResponse(Date timeStamp, String message, String details) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.details = details;
    }

    public void addValidation(String fieldName, String errorMessage){
        field.put(fieldName, errorMessage);
    }
}
