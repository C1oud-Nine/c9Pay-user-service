package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.entity.User;
import com.c9Pay.userservice.security.jwt.JwtParser;
import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.web.exception.exceptions.IllegalTokenDetailException;
import com.c9Pay.userservice.web.mvc.service.UserService;
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

    private final JwtParser jwtParser;


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
        String serialNumber = jwtParser.getSerialNumberByToken(token);
        creditClient.loadCredit(serialNumber,
                new ChargeForm(charge.getQuantity()));
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
        AccountDetails account = creditClient.getAccount(jwtParser.getSerialNumberByToken(token))
                .getBody();
        ChargeForm form = new ChargeForm(Objects.requireNonNull(account).getCredit());
        return ResponseEntity.ok(form);
    }

}
