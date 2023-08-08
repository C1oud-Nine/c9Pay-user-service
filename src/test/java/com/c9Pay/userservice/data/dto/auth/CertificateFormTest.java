package com.c9Pay.userservice.data.dto.auth;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CertificateFormTest {

    @Autowired
    private Validator validator;

    @Test
    public void testValidCertificateForm() {
        CertificateForm validForm = new CertificateForm();
        validForm.setPublicKey("validPublicKey");
        validForm.setServiceInfo(new ServiceInfo("aa", "bb"));

        Set<ConstraintViolation<CertificateForm>> violations = validator.validate(validForm);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testInvalidCertificateForm() {
        CertificateForm invalidForm = new CertificateForm();
        invalidForm.setPublicKey(""); // Blank public key
        invalidForm.setServiceInfo(null); // Null service info
        Set<ConstraintViolation<CertificateForm>> violations = validator.validate(invalidForm);
        assertFalse(violations.isEmpty());
    }
}
