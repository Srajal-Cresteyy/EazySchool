package com.eazyschool.eazyschool.annotations;


import com.eazyschool.eazyschool.validations.PasswordStrengthValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValidator {
    String message () default "Password too easy to take";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
