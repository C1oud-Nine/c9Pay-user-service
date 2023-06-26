package com.c9Pay.userservice.controller;

import com.c9Pay.userservice.dto.AuthForm;
import com.c9Pay.userservice.dto.ChargeDetail;
import com.c9Pay.userservice.dto.LoginForm;
import com.c9Pay.userservice.dto.UserDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user-service")
public class Controller {
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
    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto form){
        //@TODO: feign Client(auth-service: 개체 식별번호 생성 요청, credit-service: 계좌 생성 요청)
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/check-duplicate")
    public ResponseEntity<?> checkDuplicated(HttpServletRequest request){
        String userId = request.getHeader("userId");
        log.info("User-ID: {}", userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginForm form, HttpServletResponse response) throws IOException {
        Cookie auth = new Cookie("Auth", "dummyCookie");
        response.addCookie(auth);
        response.sendRedirect("/user-service/user");
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
