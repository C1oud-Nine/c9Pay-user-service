package com.c9Pay.userservice.client;

import brave.Response;
import com.c9Pay.userservice.web.dto.credit.ChargeDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient(name="credit-service", url="${CREDIT_SERVICE_URI:http://localhost}:8082")
public interface CreditClient {
    @PostMapping("/credit-service/account/{identifier}")
    ResponseEntity<?> createAccount(@PathVariable String identifier);

    @PostMapping("/credit-service/account/{identifier}/load")
    ResponseEntity<?> loadCredit(@PathVariable String identifier, @RequestBody ChargeDetail body);
}
