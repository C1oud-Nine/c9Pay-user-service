package com.c9Pay.userservice.data.dto.certificate;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class AuthFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidAuthForm() {
        AuthForm validAuthForm = new AuthForm("ConstructorValue", "RecognizerValue");
        Set<ConstraintViolation<AuthForm>> violations = validator.validate(validAuthForm);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidAuthForm() {
        AuthForm invalidAuthForm = new AuthForm("", "");
        Set<ConstraintViolation<AuthForm>> violations = validator.validate(invalidAuthForm);
        assertEquals(2, violations.size());
    }
}