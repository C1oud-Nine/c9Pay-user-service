package com.c9Pay.userservice.data.dto.certificate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;



public class CertificateResponseTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidCertificateResponse() {
        CertificateResponse validResponse = new CertificateResponse("ValidCertificate", "ValidSign");
        Set<ConstraintViolation<CertificateResponse>> violations = validator.validate(validResponse);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidCertificateResponse() {
        CertificateResponse invalidResponse = new CertificateResponse("", "");
        Set<ConstraintViolation<CertificateResponse>> violations = validator.validate(invalidResponse);
        assertEquals(2, violations.size());
    }
}