package com.c9Pay.userservice.data.dto.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


public class UserDtoTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testValidUserDto() {
        UserDto validUserDto = UserDto.builder()
                .username("validUsername")
                .userId("validUserId")
                .password("validPassword")
                .email("valid@example.com")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(validUserDto);
        assertEquals(0, violations.size());
    }

    @Test
    public void testInvalidUserDto() {
        UserDto invalidUserDto = UserDto.builder()
                .username("")
                .userId("")
                .password("")
                .email("invalidEmail")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(invalidUserDto);
        assertEquals(4, violations.size());
    }

    @Test
    public void testInvalidEmailFormat() {
        UserDto invalidEmailUserDto = UserDto.builder()
                .username("validUsername")
                .userId("validUserId")
                .password("validPassword")
                .email("invalidEmail")
                .build();

        Set<ConstraintViolation<UserDto>> violations = validator.validate(invalidEmailUserDto);
        assertEquals(1, violations.size());
    }
}