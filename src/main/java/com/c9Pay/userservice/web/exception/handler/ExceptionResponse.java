package com.c9Pay.userservice.web.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    private Date timeStamp;
    private String message;
    private String details;

}
