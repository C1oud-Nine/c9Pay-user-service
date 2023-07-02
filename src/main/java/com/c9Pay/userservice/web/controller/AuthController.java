package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.certificate.Certificate;
import com.c9Pay.userservice.certificate.CertificateProvider;
import com.c9Pay.userservice.web.dto.AuthForm;
import com.c9Pay.userservice.web.dto.CertificateForm;
import com.c9Pay.userservice.web.dto.CertificateResponse;
import com.c9Pay.userservice.web.dto.PublicKeyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CertificateProvider certificateProvider;
    @PostMapping("/auth")
    public ResponseEntity<?> authenticationResponse(@RequestBody AuthForm form){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public-key")
    public ResponseEntity<PublicKeyResponse> getPublicKey(){
        PublicKey publicKey = certificateProvider.getPublicKey();

        String base64Encoded = new String(Base64.getEncoder().encode(publicKey.getEncoded()),
                StandardCharsets.UTF_8);

        return ResponseEntity.ok(new PublicKeyResponse(base64Encoded));
    }

    @PostMapping("/certificate")
    public ResponseEntity<CertificateResponse> createCertificate(@RequestBody CertificateForm form){
        Optional<Certificate> nullable = certificateProvider.getCertificate(form);

        return nullable
                .map(certificate ->
                        ResponseEntity.ok(new CertificateResponse(certificate.getCertificate(), certificate.getSign())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Certificate cert){
        Optional<CertificateForm> nullable = certificateProvider.decrypt(cert, CertificateForm.class);

        return nullable
                .map(certificateForm ->
                        ResponseEntity.ok(new CertificateForm(certificateForm.getSerialNumber())))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
