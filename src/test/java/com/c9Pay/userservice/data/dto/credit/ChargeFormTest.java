package com.c9Pay.userservice.data.dto.credit;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ChargeFormTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidChargeForm() {
        ChargeForm validChargeForm = new ChargeForm(10L);
        Set<ConstraintViolation<ChargeForm>> violations = validator.validate(validChargeForm);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidChargeForm() {
        ChargeForm invalidChargeForm = new ChargeForm(-5L);
        Set<ConstraintViolation<ChargeForm>> violations = validator.validate(invalidChargeForm);
        assertEquals(1, violations.size());
    }
}