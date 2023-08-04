package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.certificate.CertificateProvider;
import com.c9Pay.userservice.data.dto.certificate.AuthForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final CertificateProvider certificateProvider;
    @PostMapping("/internal/auth")
    public ResponseEntity<?> authenticationResponse(@RequestBody AuthForm form){
        return ResponseEntity.ok().build();
    }

}
