package com.c9Pay.userservice.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="auth-service")
public interface AuthClient {

    @PostMapping("/auth-service/serial-number")
    Object createSerialNumber(@RequestBody Object responseAddress);

    @GetMapping("/auth-service/verify-code")
    Object createVerifyCode(HttpServletRequest request, HttpServletResponse response);
}
