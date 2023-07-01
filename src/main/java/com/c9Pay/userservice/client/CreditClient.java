package com.c9Pay.userservice.client;

import com.c9Pay.userservice.web.dto.ChargeDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="credit-service")
public interface CreditClient {
    @PostMapping("/credit-service/account/{identifier}")
    Object createAccount(@PathVariable String identifier);

    @PostMapping("/credit-service/account/{identifier}/load")
    Object loadCredit(@PathVariable String identifier, @RequestBody ChargeDetail body);
}
