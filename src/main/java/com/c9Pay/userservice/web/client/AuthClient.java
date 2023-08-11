package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.auth.ExchangeToken;
import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="auth-service", url="${AUTH_SERVICE_URI:http://localhost}:8081")
public interface AuthClient {

    @PostMapping("/auth-service/serial-number")
    ResponseEntity<SerialNumberResponse> getSerialNumber();

    @GetMapping("/auth-service/serial-number/{serialNumber}/exchange")
    ResponseEntity<ExchangeToken> createQR(@PathVariable String serialNumber);

    @GetMapping("/auth-service/serial-number/{serialNumber}")
    ResponseEntity<?> validateSerialNumber(@PathVariable String serialNumber);
}
