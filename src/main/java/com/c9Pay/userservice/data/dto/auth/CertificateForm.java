package com.c9Pay.userservice.data.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateForm {

    @NotBlank
    private String publicKey;

    @NotNull
    private ServiceInfo serviceInfo;
}
