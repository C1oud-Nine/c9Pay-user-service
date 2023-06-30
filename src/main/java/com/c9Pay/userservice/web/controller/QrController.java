package com.c9Pay.userservice.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QrController {

    @GetMapping("/qr")
    public ResponseEntity<String> createQr(@CookieValue("Auth") String auth){
        //@TODO: feign-client(auth-service: qr 생성 요청)
        return ResponseEntity.ok("Authentication number");
    }
}
