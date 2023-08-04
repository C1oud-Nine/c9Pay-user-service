package com.c9Pay.userservice.web.mvc.controller;

import com.c9Pay.userservice.web.client.CreditClient;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import com.c9Pay.userservice.web.exception.IllegalTokenDetailException;
import com.c9Pay.userservice.web.mvc.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.c9Pay.userservice.constant.CookieConstant.AUTHORIZATION_HEADER;

@Slf4j
@RestController
@RequestMapping("/api/credit")
@RequiredArgsConstructor
public class CreditController {
    private final CreditClient creditClient;

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> chargeCredit(@RequestBody ChargeForm charge, @RequestHeader(AUTHORIZATION_HEADER) String token){
        log.info("Before call credit service");
        String ID = parseToken(token);
        log.info("id {}", ID);
        String serialNumber = userService
                .findById(Long.valueOf(ID))
                .getSerialNumber().toString();

        log.info("serial number: {}", serialNumber);
        log.info("quantity: {}", charge.getQuantity());

        creditClient.loadCredit(serialNumber, new ChargeForm(charge.getQuantity()));

        log.info("After call credit service, serialNumber: {}", serialNumber);
        return ResponseEntity.ok().build();
    }

    private String parseToken(String token) {
        if(token.length() >= 7){
            String parsedToken = token.substring(7);
            return jwtTokenUtil.extractId(parsedToken);
        }
        throw new IllegalTokenDetailException();
    }

}
