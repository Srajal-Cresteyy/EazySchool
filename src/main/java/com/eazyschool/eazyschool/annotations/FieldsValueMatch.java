package com.eazyschool.eazyschool.annotations;


import com.eazyschool.eazyschool.validations.FieldValueMatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldValueMatchValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsValueMatch {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "Field Values don't match with each other !";
    String field();
    String fieldMatch();
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface  List {
        FieldsValueMatch[] value();
    }
}
