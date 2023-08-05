package com.c9Pay.userservice.data.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateForm {
    private String publicKey;
    private ServiceInfo serviceInfo;
}
