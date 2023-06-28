package com.c9Pay.userservice.controller;

import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.dto.UserDto;
import com.c9Pay.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUserDetail(@CookieValue("Auth") String token){
        UserDto dummy = UserDto.builder()
                .userId("dummy")
                .email("abc@gmail.com")
                .username("alice")
                .build();
        return ResponseEntity.ok(dummy);
    }

    @GetMapping("/check-duplicate")
    public ResponseEntity<?> checkDuplicated(HttpServletRequest request){
        boolean isDuplicated = userService.validateDuplicateUserId(request.getHeader("userId"));
        if(isDuplicated) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }


    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto form){
        try{
            User joinUser = form.toEntity();
            userService.signUp(joinUser);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
