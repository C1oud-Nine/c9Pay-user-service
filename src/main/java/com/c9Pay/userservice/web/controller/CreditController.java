package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.web.dto.credit.ChargeDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/credit")
public class CreditController {
    @PostMapping
    public ResponseEntity<?> chargeCredit(@RequestBody ChargeDetail charge, @CookieValue("Auth")String auth){
        log.info("Before call credit service");
        //@TODO: feign-client(credit-service: 크레딧 충전 요청)
        log.info("After call credit service");
        return ResponseEntity.ok().build();
    }

}
