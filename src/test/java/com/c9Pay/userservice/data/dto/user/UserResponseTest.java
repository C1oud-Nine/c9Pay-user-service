package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class UserResponseTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUserResponse() {
        UserResponse validUserResponse = UserResponse.builder()
                .username("validUsername")
                .userId("validUserId")
                .email("valid@example.com")
                .serialNumber(UUID.randomUUID().toString())
                .build();

        Set<ConstraintViolation<UserResponse>> violations = validator.validate(validUserResponse);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidUserResponse() {
        UserResponse invalidUserResponse = UserResponse.builder()
                .username("")
                .userId("")
                .email("invalidEmail")
                .serialNumber("")
                .build();

        Set<ConstraintViolation<UserResponse>> violations = validator.validate(invalidUserResponse);
        assertEquals(4, violations.size());
    }

    @Test
    public void testInvalidEmailFormat() {
        UserResponse invalidEmailUserResponse = UserResponse.builder()
                .username("validUsername")
                .userId("validUserId")
                .email("invalidEmail")
                .serialNumber(UUID.randomUUID().toString())
                .build();

        Set<ConstraintViolation<UserResponse>> violations = validator.validate(invalidEmailUserResponse);
        assertEquals(1, violations.size());
    }
}