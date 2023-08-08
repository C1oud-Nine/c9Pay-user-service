package com.c9Pay.userservice.data.dto.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SerialNumberResponseTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidSerialNumberResponse() {
        SerialNumberResponse validResponse = new SerialNumberResponse(UUID.randomUUID());
        Set<ConstraintViolation<SerialNumberResponse>> violations = validator.validate(validResponse);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidSerialNumberResponse() {
        SerialNumberResponse invalidResponse = new SerialNumberResponse(null);
        Set<ConstraintViolation<SerialNumberResponse>> violations = validator.validate(invalidResponse);
        assertEquals(1, violations.size());
    }
}