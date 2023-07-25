package com.c9Pay.userservice.web.controller;

import com.c9Pay.userservice.web.dto.credit.ChargeDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/credit")
public class CreditController {
    @PostMapping
    public ResponseEntity<?> chargeCredit(@RequestBody ChargeDetail charge, @CookieValue("Auth")String auth){
        //@TODO: feign-client(credit-service: 크레딧 충전 요청)
        return ResponseEntity.ok().build();
    }

}
