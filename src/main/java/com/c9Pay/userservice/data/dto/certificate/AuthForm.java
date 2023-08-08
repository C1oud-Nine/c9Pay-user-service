package com.c9Pay.userservice.data.dto.certificate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthForm {

    @NotBlank
    private String constructor;
    @NotBlank
    private String recognizer;
}
