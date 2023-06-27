package com.c9Pay.userservice.controller;

import com.c9Pay.userservice.dto.AuthForm;
import com.c9Pay.userservice.dto.ChargeDetail;
import com.c9Pay.userservice.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class UserController {
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserDetail(@CookieValue("Auth") String token){
        UserDto dummy = UserDto.builder()
                .userId("dummy")
                .email("abc@gmail.com")
                .credit(10000L)
                .username("alice")
                .build();
        return ResponseEntity.ok(dummy);
    }

    @GetMapping("/user/check-duplicate")
    public ResponseEntity<?> checkDuplicated(HttpServletRequest request){
        String userId = request.getHeader("userId");
        log.info("User-ID: {}", userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/qr")
    public ResponseEntity<String> createQr(@CookieValue("Auth") String auth){
        log.info("Auth token: {}", auth);
        //@TODO: feign-client(auth-service: qr 생성 요청)
        return ResponseEntity.ok("Authentication number");
    }

    @PostMapping("/credit")
    public ResponseEntity<?> chargeCredit(@RequestBody ChargeDetail charge, @CookieValue("Auth")String auth){
        log.info("Auth token: {}", auth);
        //@TODO: feign-client(credit-service: 크레딧 충전 요청)
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticationResponse(@RequestBody AuthForm form){
        log.info("생성자: {}, 인식자: {}", form.getConstructor(), form.getRecognizer());
        return ResponseEntity.ok().build();
    }

}
