package com.fm.alSoukBk.security.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class PasswordConstraintValidator
        implements ConstraintValidator<ValidPassword, String> {

    @Autowired
    private CustomPasswordValidator customValidator;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        try {
            customValidator.validate(password);
            return true;
        } catch (IllegalArgumentException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addConstraintViolation();
            return false;
        }
    }
}