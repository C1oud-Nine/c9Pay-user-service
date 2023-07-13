package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.certificate.Certificate;
import com.c9Pay.userservice.certificate.CertificateProvider;
import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.certificate.CertificateResponse;
import com.c9Pay.userservice.web.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QrController {

    private final CertificateProvider certificateProvider;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    @GetMapping("/qr")
    public ResponseEntity<CertificateResponse> createQr(@CookieValue(AUTHORIZATION_HEADER) String token){

        Authentication authentication = tokenProvider.getAuthentication(token);
        User findId = userService.findById(Long.valueOf(authentication.getName()));
        UUID serialNumber = findId.getSerialNumber();
        certificateProvider.getCertificate(serialNumber);

        Optional<Certificate> nullable = certificateProvider.getCertificate(serialNumber);

        return nullable
                .map(certificate -> ResponseEntity.ok(new CertificateResponse(certificate.getCertificate(), certificate.getSign())))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }
}
