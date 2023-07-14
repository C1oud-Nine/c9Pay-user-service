package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.user.UserDto;
import com.c9Pay.userservice.web.dto.user.UserUpdateParam;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final TokenProvider tokenProvider;


    @GetMapping
    public ResponseEntity<UserDto> getUserDetail(@CookieValue(AUTHORIZATION_HEADER) String token){

        try{
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

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@CookieValue(AUTHORIZATION_HEADER) String token){
        try{
            Authentication authentication = parseToken(token);
            Long targetId = Long.valueOf(authentication.getName());
            userService.deleteUserById(targetId);
            //@TODO: 사용자 탈퇴시 credit service의 계좌 삭제 요청
            return ResponseEntity.ok("삭제 요청 성공.");
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUserInfo(@CookieValue(AUTHORIZATION_HEADER) String token, @RequestBody UserUpdateParam param){
        try{
            Authentication authentication = parseToken(token);
            Long targetId = Long.valueOf(authentication.getName());
            userService.updateUserById(targetId, param);
            return ResponseEntity.ok(param);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/serial-number")
    public ResponseEntity<?> getSerialNumber(@CookieValue(AUTHORIZATION_HEADER) String token){
        try{
            Authentication authentication = parseToken(token);
            User findUser = userService.findById(Long.valueOf(authentication.getName()));
            return ResponseEntity.ok(findUser.getSerialNumber());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
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

    private Authentication parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return tokenProvider.getAuthentication(parsedToken);
        }
        throw new IllegalStateException("잘못된 토큰");
    }
}
