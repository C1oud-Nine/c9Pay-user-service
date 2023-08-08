package com.c9Pay.userservice.data.dto.certificate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponse {

    @NotBlank
    private String certificate;

    @NotBlank
    private String sign;
}
