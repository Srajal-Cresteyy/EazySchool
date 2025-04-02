package com.eazyschool.eazyschool.validations;

import com.eazyschool.eazyschool.annotations.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator,String> {
    private List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator constraintAnnotation) {
        weakPasswords = Arrays.asList("12345","qwerty","password");
    }

    @Override
    public boolean isValid(String passwordValue, ConstraintValidatorContext context) {
        return passwordValue != null && !passwordValue.isEmpty() && ! weakPasswords.contains(passwordValue);
    }
}
