package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="credit-service", url="${CREDIT_SERVICE_URI:http://localhost}:8082")
public interface CreditClient {
    @PostMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> createAccount(@PathVariable String identifier);


    @Headers("Accept: application/json")
    @PostMapping(value = "/credit-service/account/{identifier}/load",  produces = "application/json")
    ResponseEntity<?> loadCredit(@PathVariable String identifier, ChargeForm form);

    @DeleteMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> deleteAccount(@PathVariable String identifier);
}
