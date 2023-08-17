package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.data.dto.user.UserResponse;
import com.c9Pay.userservice.web.client.AuthClient;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import com.c9Pay.userservice.web.exception.exceptions.IllegalTokenDetailException;
import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import com.c9Pay.userservice.web.exception.exceptions.TokenGenerationFailureException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.c9Pay.userservice.constant.BearerConstant.BEARER_PREFIX;
import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

/**
 * 사용자 정보 처리에 대한 컨트롤러
 * @see com.c9Pay.userservice.web.client.AuthClient
 * @see com.c9Pay.userservice.web.client.CreditClient
 * @see com.c9Pay.userservice.data.entity.User
 * @see com.c9Pay.userservice.security.jwt.JwtTokenUtil
 * @see com.c9Pay.userservice.data.dto.auth.SerialNumberResponse
 * @see com.c9Pay.userservice.data.dto.user.UserDto
 * @see com.c9Pay.userservice.data.dto.user.UserUpdateParam
 * @see com.c9Pay.userservice.web.exception.exceptions.TokenGenerationFailureException
 * @see com.c9Pay.userservice.web.mvc.service.UserService
 * @see jakarta.servlet.http.Cookie
 * @see jakarta.servlet.http.HttpServletRequest
 * @see jakarta.servlet.http.HttpServletResponse
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final CreditClient creditClient;
    private final AuthClient authClient;
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 신규 회원을 등록한다.
     *
     * @param form 사용자 회원가입에 필요한 정보를 담고 있는 UserDto 정보
     * @return 회원가입이 성공한 경우 사용자의 객체식별번호를 포함하는 ResponseEntity 반환
     * @throws TokenGenerationFailureException auth-service에서 개체식별번호를 받아 오지 못할 경우 발생하는 예외
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @Valid UserDto form){
        log.info("Starting registration for a new user account");
        SerialNumberResponse serialNumberResponse = authClient.getSerialNumber().getBody();
        if(serialNumberResponse == null) throw new TokenGenerationFailureException();
        String serialNumber = serialNumberResponse.getSerialNumber().toString();
        log.info("Entity identification number generation:{}", serialNumber);
        creditClient.createAccount(serialNumber);
        User joinUser = form.toEntity(serialNumberResponse.getSerialNumber());
        userService.signUp(joinUser);
        log.info("Registration success");
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 사용자의 상세 정보를 조회한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return 특정 사용자의 상세 정보를 포함하는 ResponseEntity 반환
     */
    @GetMapping
    public ResponseEntity<?> getUserDetail(@CookieValue(AUTHORIZATION_HEADER) String token){
        String ID = parseToken(token);
        Long targetId = Long.valueOf(ID);
        User findUser = userService.findById(targetId);
        UserResponse response = UserResponse.mapping(findUser);
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 사용자의 계정을 삭제한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @param request HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 계정 삭제 요청의 성공 여부를 담은 ResponseEntity 반환
     */
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@CookieValue(AUTHORIZATION_HEADER) String token, HttpServletRequest request
            , HttpServletResponse response){
        String ID = parseToken(token);
        Long targetId = Long.valueOf(ID);
        String serialNumber = userService
                .findById(targetId)
                .getSerialNumber().toString();

        response.addCookie(new Cookie(AUTHORIZATION_HEADER, null));
        userService.deleteUserById(targetId);
        creditClient.deleteAccount(serialNumber);
        return ResponseEntity.ok().build();
    }

    /**
     * 특정 사용자의 정보를 업데이트한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @param param 업데이트할 사용자의 정보를 담은 UserUpdateParam 객체
     * @param response HTTP 응답 객체
     * @return 업데이트된 사용자 정보를 담은 ResponseEntity 반환
     */
    @PutMapping
    public ResponseEntity<?> updateUserInfo(@CookieValue(AUTHORIZATION_HEADER) String token,
                                            @Valid@RequestBody UserUpdateParam param,
                                            HttpServletResponse response){
        String ID = parseToken(token);
        log.info("ID: {}", ID);
        Long targetId = Long.valueOf(ID);
        String password = param.getPassword();
        userService.updateUserById(targetId, param);
        String newToken = userService.authenticate(param.getUserId(), password);
        response.addCookie(new Cookie(AUTHORIZATION_HEADER, BEARER_PREFIX+ newToken));
        return ResponseEntity.ok(param);
    }

    /**
     * 현재 로그인한 사용자의 식별번호를 조회한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return 현재 로그인한 사용자의 개체식별번호를 포함하는 ResponseEntity 반환
     */
    @GetMapping("/serial-number")
    public ResponseEntity<?> getSerialNumber(@CookieValue(AUTHORIZATION_HEADER) String token){
        String ID = parseToken(token);
        User findUser = userService.findById(Long.valueOf(ID));
        return ResponseEntity.ok(findUser.getSerialNumber().toString());
    }

    /**
     * 사용자 아이디 중복 여부를 확인한다
     *
     * @param request HTTP 요청 객체
     * @return 사용자 아이디가 중복되지 않으면 OK응답, 중복될 경우 Bad Request 응답을 반환한다.
     */
    @GetMapping("/check-duplicate")
    public ResponseEntity<?> checkDuplicated(HttpServletRequest request){

        return userService.validateDuplicateUserId(request.getHeader("userId"))?
        ResponseEntity.ok().build(): ResponseEntity.badRequest().build();
    }

    /**
     * 주어진 인증토큰에서 사용자 ID를 추출한다.
     *
     * @param token 인증 토큰 문자열
     * @return 추출된 사용자 ID
     * @throws IllegalTokenDetailException 토큰이 유효하지 않거나 형식에 맞지 않을 경우 발생하는 예외
     */
    private String parseToken(String token) {
        if(token == null || token.length() < 7) throw new IllegalTokenDetailException();
        String parsedToken = token.substring(7);
        return jwtTokenUtil.extractId(parsedToken);
    }
}
