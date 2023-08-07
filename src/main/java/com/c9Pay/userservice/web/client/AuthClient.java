package com.c9Pay.userservice.web.client;

import com.c9Pay.userservice.data.dto.auth.CertificateForm;
import com.c9Pay.userservice.data.dto.auth.CertificateResponse;
import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="auth-service", url="${AUTH_SERVICE_URI:http://localhost}:8081")
public interface AuthClient {

    @PostMapping("/auth-service/serial-number")
    ResponseEntity<SerialNumberResponse> getSerialNumber();

    @PostMapping("/auth-service/certificate")
    ResponseEntity<CertificateResponse> getCertificate( CertificateForm certificateForm);


    @GetMapping("/auth-service/serial-number")
    ResponseEntity<?> validateSerialNumber(@RequestParam String serialNumber);
}
