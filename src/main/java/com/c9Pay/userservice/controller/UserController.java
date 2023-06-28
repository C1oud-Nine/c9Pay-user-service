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
@RequestMapping("/user")
public class UserController {
    @GetMapping
    public ResponseEntity<UserDto> getUserDetail(@CookieValue("Auth") String token){
        UserDto dummy = UserDto.builder()
                .userId("dummy")
                .email("abc@gmail.com")
                .credit(10000L)
                .username("alice")
                .build();
        return ResponseEntity.ok(dummy);
    }

    @GetMapping("/check-duplicate")
    public ResponseEntity<?> checkDuplicated(HttpServletRequest request){
        String userId = request.getHeader("userId");
        log.info("User-ID: {}", userId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto form){
        //@TODO: feign Client(auth-service: 개체 식별번호 생성 요청, credit-service: 계좌 생성 요청)
        return ResponseEntity.ok().build();
    }
}
