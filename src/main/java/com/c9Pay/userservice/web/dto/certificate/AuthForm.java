package com.c9Pay.userservice.web.dto.certificate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthForm {
    private String constructor;
    private String recognizer;
}