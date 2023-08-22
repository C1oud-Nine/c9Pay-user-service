package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.web.exception.exceptions.IllegalTokenDetailException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;


/**
 * 크레딧 서비스와 통신을 관리하는 컨트롤러
 *
 * @see com.c9Pay.userservice.data.dto.credit.AccountDetails
 * @see com.c9Pay.userservice.data.entity.User
 * @see com.c9Pay.userservice.web.client.CreditClient
 * @see com.c9Pay.userservice.security.jwt.JwtTokenUtil
 * @see com.c9Pay.userservice.data.dto.credit.ChargeForm
 * @see com.c9Pay.userservice.web.exception.exceptions.IllegalTokenDetailException
 * @see com.c9Pay.userservice.web.mvc.service.UserService
 * @see jakarta.servlet.http.HttpServletRequest
 * @see jakarta.servlet.http.HttpServletResponse
 */
@Slf4j
@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditController {
    private final CreditClient creditClient;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    /**
     * 사용자 크레딧을 충전한다.
     *
     * @param charge 크레딧 충전 정보를 담고 있는 ChargeForm 객체
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return 크레딧 충전이 성공한 경우 OK 응답을 반환
     */
    @PostMapping
    public ResponseEntity<?> chargeCredit(@Valid @RequestBody ChargeForm charge,
                                          @CookieValue(AUTHORIZATION_HEADER) String token){
        String ID = parseToken(token);
        String serialNumber = userService
                .findById(Long.valueOf(ID))
                .getSerialNumber().toString();
        creditClient.loadCredit(serialNumber, new ChargeForm(charge.getQuantity()));
        return ResponseEntity.ok().build();
    }

    /**
     * 사용자 크레딧 정보를 조회한다.
     *
     * @param token 사용자의 인증 토큰이 포함된 쿠키 값
     * @return 사용자의 현재 크레딧 정보를 담은 ResponseEntity 반환
     */
    @GetMapping
    public  ResponseEntity<?> getCredit(@CookieValue(AUTHORIZATION_HEADER) String token){
        Long id = Long.valueOf(parseToken(token));
        User findUser = userService.findById(id);
        ResponseEntity<AccountDetails> account = creditClient.getAccount(findUser.getSerialNumber().toString());
        ChargeForm form = new ChargeForm(Objects.requireNonNull(account.getBody()).getCredit());
        return ResponseEntity.ok(form);
    }

    /**
     * 주어진 인증 토큰에서 사용자 ID를 추출한다.
     *
     * @param token 인증 토큰 문자열
     * @return 추출된 사용자 ID
     * @throws IllegalTokenDetailException 토큰이 유효하지 않거나 형식에 맞지 않을 경우 발생하는 예외
     */
    private String parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return jwtTokenUtil.extractId(parsedToken);
        }
        throw new IllegalTokenDetailException();
    }

}
