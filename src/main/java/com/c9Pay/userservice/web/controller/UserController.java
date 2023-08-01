package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.client.AuthClient;
import com.c9Pay.userservice.client.CreditClient;
import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.web.dto.auth.SerialNumberResponse;
import com.c9Pay.userservice.web.exception.IllegalTokenDetailException;
import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.user.UserDto;
import com.c9Pay.userservice.web.dto.user.UserUpdateParam;
import com.c9Pay.userservice.web.exception.handler.TokenGenerationFailureException;
import com.c9Pay.userservice.web.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final CreditClient creditClient;
    private final AuthClient authClient;
    private final UserService userService;
    private final TokenProvider tokenProvider;


    @GetMapping
    public ResponseEntity<UserDto> getUserDetail(@CookieValue(AUTHORIZATION_HEADER) String token){

        Authentication authentication = parseToken(token);
        Long targetId = Long.valueOf(authentication.getName());
        User findUser = userService.findById(targetId);
        UserDto dto = UserDto.builder()
                .userId(findUser.getUserId())
                .email(findUser.getEmail())
                .username(findUser.getUsername())
                .password(findUser.getPassword())
                .build();
        return ResponseEntity.ok(dto);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@CookieValue(AUTHORIZATION_HEADER) String token){
        Authentication authentication = parseToken(token);
        Long targetId = Long.valueOf(authentication.getName());
        String serialNumber = userService
                .findById(Long.valueOf(authentication.getName()))
                .getSerialNumber().toString();

        userService.deleteUserById(targetId);
        creditClient.deleteAccount(serialNumber);
        return ResponseEntity.ok("삭제 요청 성공.");
    }

    @PutMapping
    public ResponseEntity<?> updateUserInfo(@CookieValue(AUTHORIZATION_HEADER) String token, @RequestBody UserUpdateParam param){
        Authentication authentication = parseToken(token);
        Long targetId = Long.valueOf(authentication.getName());
        userService.updateUserById(targetId, param);
        return ResponseEntity.ok(param);
    }

    @GetMapping("/serial-number")
    public ResponseEntity<?> getSerialNumber(@CookieValue(AUTHORIZATION_HEADER) String token){
        Authentication authentication = parseToken(token);
        User findUser = userService.findById(Long.valueOf(authentication.getName()));
        return ResponseEntity.ok(findUser.getSerialNumber());
    }

    @GetMapping("/check-duplicate")
    public ResponseEntity<?> checkDuplicated(HttpServletRequest request){

        return userService.validateDuplicateUserId(request.getHeader("userId"))?
        ResponseEntity.ok().build(): ResponseEntity.badRequest().build();
    }
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto form){
        log.info("Starting registration for a new user account");
        SerialNumberResponse serialNumberResponse = authClient.createSerialNumber().getBody();
        if(serialNumberResponse == null) throw new TokenGenerationFailureException();
        String serialNumber = serialNumberResponse.getSerialNumber().toString();
        log.info("Entity identification number generation:{}", serialNumber);
        creditClient.createAccount(serialNumber);
        User joinUser = form.toEntity(serialNumberResponse.getSerialNumber());
        userService.signUp(joinUser);
        log.info("Registration success");
        return ResponseEntity.ok().build();
    }

    private Authentication parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return tokenProvider.getAuthentication(parsedToken);
        }
        throw new IllegalTokenDetailException();
    }
}
