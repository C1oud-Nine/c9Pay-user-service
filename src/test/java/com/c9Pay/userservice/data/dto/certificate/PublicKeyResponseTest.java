package com.c9Pay.userservice.data.dto.certificate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class PublicKeyResponseTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidPublicKeyResponse() {
        PublicKeyResponse validResponse = new PublicKeyResponse("ValidPublicKey");
        Set<ConstraintViolation<PublicKeyResponse>> violations = validator.validate(validResponse);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidPublicKeyResponse() {
        PublicKeyResponse invalidResponse = new PublicKeyResponse("");
        Set<ConstraintViolation<PublicKeyResponse>> violations = validator.validate(invalidResponse);
        assertEquals(1, violations.size());
    }
}