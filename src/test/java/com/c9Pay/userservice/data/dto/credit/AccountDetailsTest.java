package com.c9Pay.userservice.data.dto.credit;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
public class AccountDetailsTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidAccountDetails() {
        AccountDetails validAccountDetails = new AccountDetails("ValidSerialNumber", 100L);
        Set<ConstraintViolation<AccountDetails>> violations = validator.validate(validAccountDetails);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidAccountDetails() {
        AccountDetails invalidAccountDetails = new AccountDetails("", -10L);
        Set<ConstraintViolation<AccountDetails>> violations = validator.validate(invalidAccountDetails);
        assertEquals(2, violations.size());
    }
}