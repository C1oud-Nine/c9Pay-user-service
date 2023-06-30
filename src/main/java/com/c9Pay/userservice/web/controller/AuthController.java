package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.web.dto.AuthForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping("/auth")
    public ResponseEntity<?> authenticationResponse(@RequestBody AuthForm form){
        return ResponseEntity.ok().build();
    }

}
