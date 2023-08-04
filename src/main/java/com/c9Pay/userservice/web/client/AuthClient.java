package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name="auth-service", url="${AUTH_SERVICE_URI:http://localhost}:8081")
public interface AuthClient {

    @PostMapping("/auth-service/serial-number")
    ResponseEntity<SerialNumberResponse> createSerialNumber();

}
