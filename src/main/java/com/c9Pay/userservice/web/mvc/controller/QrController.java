package com.c9Pay.userservice.web.mvc.controller;


import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.data.dto.certificate.CertificateResponse;
import com.c9Pay.userservice.web.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QrController {


    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;
    /*@GetMapping("/api/qr")
    public ResponseEntity<CertificateResponse> createQr(@RequestHeader(AUTHORIZATION_HEADER) String token){


        return nullable
                .map(certificate -> ResponseEntity.ok(new CertificateResponse(certificate.getCertificate(), certificate.getSign())))
                .orElseGet(() -> ResponseEntity.badRequest().build());

    }*/
}
