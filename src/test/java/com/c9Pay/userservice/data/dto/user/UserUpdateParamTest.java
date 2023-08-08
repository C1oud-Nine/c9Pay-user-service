package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;



public class UserUpdateParamTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUserUpdateParam() {
        UserUpdateParam validUserUpdateParam = new UserUpdateParam("validUsername", "validPassword", "valid@example.com", "validUserId");
        Set<ConstraintViolation<UserUpdateParam>> violations = validator.validate(validUserUpdateParam);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidUserUpdateParam() {
        UserUpdateParam invalidUserUpdateParam = new UserUpdateParam("", "", "invalidEmail", "");
        Set<ConstraintViolation<UserUpdateParam>> violations = validator.validate(invalidUserUpdateParam);
        assertEquals(4, violations.size());
    }

    @Test
    public void testInvalidEmailFormat() {
        UserUpdateParam invalidEmailUserUpdateParam = new UserUpdateParam("validUsername", "validPassword", "invalidEmail", "validUserId");
        Set<ConstraintViolation<UserUpdateParam>> violations = validator.validate(invalidEmailUserUpdateParam);
        assertEquals(1, violations.size());
    }
}