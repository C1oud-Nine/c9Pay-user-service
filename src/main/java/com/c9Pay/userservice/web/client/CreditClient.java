package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.credit.AccountDetails;
import com.c9Pay.userservice.data.dto.credit.ChargeForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name="credit-service", url="${CREDIT_SERVICE_URI:http://localhost}:8082")
public interface CreditClient {
    @PostMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> createAccount(@PathVariable("identifier") String identifier);


    @PostMapping("/credit-service/account/{identifier}/load")
    ResponseEntity<?> loadCredit(@PathVariable("identifier") String identifier, ChargeForm form);

    @DeleteMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> deleteAccount(@PathVariable("identifier") String identifier);

    @GetMapping("/credit-service/account/{identifier}")
    ResponseEntity<AccountDetails> getAccount(@PathVariable("identifier") String identifier);
}
