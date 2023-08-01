package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.client.CreditClient;
import com.c9Pay.userservice.constant.CookieConstant;
import com.c9Pay.userservice.entity.User;
import com.c9Pay.userservice.jwt.TokenProvider;
import com.c9Pay.userservice.web.dto.credit.ChargeForm;
import com.c9Pay.userservice.web.exception.IllegalTokenDetailException;
import com.c9Pay.userservice.web.service.UserService;
import feign.Feign;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditController {
    private final CreditClient creditClient;
    private final TokenProvider tokenProvider;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> chargeCredit(@RequestBody ChargeForm charge, @CookieValue(AUTHORIZATION_HEADER) String token){
        log.info("Before call credit service");
        Authentication authentication = parseToken(token);
        log.info("id {}", authentication.getName());
        String serialNumber = userService
                .findById(Long.valueOf(authentication.getName()))
                .getSerialNumber().toString();

        log.info("serial number: {}", serialNumber);
        log.info("quantity: {}", charge.getQuantity());

        creditClient.loadCredit(serialNumber, new ChargeForm(charge.getQuantity()));

        log.info("After call credit service, serialNumber: {}", serialNumber);
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
