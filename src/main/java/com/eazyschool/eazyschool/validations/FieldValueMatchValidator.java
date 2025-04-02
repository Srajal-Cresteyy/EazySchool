package com.eazyschool.eazyschool.validations;

import com.eazyschool.eazyschool.annotations.FieldsValueMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldValueMatchValidator implements ConstraintValidator<FieldsValueMatch,Object> {
    String field;
    String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        field = constraintAnnotation.field();
        fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {


        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        if(fieldValue != null){
            return fieldValue.equals(fieldMatchValue);
        }
        else return fieldMatchValue == null;
    }
}
