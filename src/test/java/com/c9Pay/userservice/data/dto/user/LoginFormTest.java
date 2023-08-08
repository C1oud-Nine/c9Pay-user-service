package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class LoginFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidLoginForm() {
        LoginForm validLoginForm = new LoginForm("validUserId", "validPassword");
        Set<ConstraintViolation<LoginForm>> violations = validator.validate(validLoginForm);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidLoginForm() {
        LoginForm invalidLoginForm = new LoginForm("", "");
        Set<ConstraintViolation<LoginForm>> violations = validator.validate(invalidLoginForm);
        assertEquals(2, violations.size());
    }
}