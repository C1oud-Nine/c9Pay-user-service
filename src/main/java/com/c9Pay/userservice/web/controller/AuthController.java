package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.certificate.CertificateProvider;
import com.c9Pay.userservice.web.dto.certificate.AuthForm;
import com.c9Pay.userservice.web.dto.certificate.PublicKeyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CertificateProvider certificateProvider;
    @PostMapping("/internal/auth")
    public ResponseEntity<?> authenticationResponse(@RequestBody AuthForm form){
        return ResponseEntity.ok().build();
    }

}
