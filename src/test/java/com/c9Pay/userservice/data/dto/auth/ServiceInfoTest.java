package com.c9Pay.userservice.data.dto.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class ServiceInfoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidServiceInfo() {
        ServiceInfo validServiceInfo = new ServiceInfo("Valid Name", "https://example.com");
        Set<ConstraintViolation<ServiceInfo>> violations = validator.validate(validServiceInfo);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidServiceInfo() {
        ServiceInfo invalidServiceInfo = new ServiceInfo("", null);
        Set<ConstraintViolation<ServiceInfo>> violations = validator.validate(invalidServiceInfo);
        assertFalse(violations.isEmpty());
    }
}