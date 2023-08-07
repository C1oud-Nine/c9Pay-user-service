package com.c9Pay.userservice.web.client;


import com.c9Pay.userservice.data.dto.auth.CertificateForm;
import com.c9Pay.userservice.data.dto.auth.CertificateResponse;
import com.c9Pay.userservice.data.dto.auth.SerialNumberResponse;
import com.c9Pay.userservice.data.dto.auth.ServiceInfo;
import com.c9Pay.userservice.security.jwt.JwtAuthenticationFilter;
import com.c9Pay.userservice.security.jwt.JwtTokenUtil;
import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthClientTest {


    @Autowired private AuthClient authClient;
    @Value("${rsa.public-key}")
    private String publicKey;
    @Test
    @DisplayName("Getting object identification numbers from authentication services")
    public void testGetSerialNumber() {
        ResponseEntity<SerialNumberResponse> response = authClient.getSerialNumber();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }

    @Test
    @DisplayName("validate a generated serial number")
    public void verify(){
        String serialNumber = Objects.requireNonNull(authClient.getSerialNumber()
                        .getBody()).getSerialNumber().toString();

        log.info("Generated Serial Number is :{} ",serialNumber);
        ResponseEntity<?> response = authClient.validateSerialNumber(serialNumber);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("I get a certificate from the certification service.")
    public void testGetCertification(){
        ServiceInfo serviceInfo = new ServiceInfo("test", "test/endpoint");
        CertificateForm certificateForm = new CertificateForm(publicKey,serviceInfo);
        ResponseEntity<CertificateResponse> response = authClient.getCertificate(certificateForm);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}