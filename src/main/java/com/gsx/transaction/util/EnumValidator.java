package com.gsx.transaction.util;

import com.gsx.transaction.annotation.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Object> {

    private Enum<?>[] values;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.values = constraintAnnotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        for (Enum<?> enumValue : values) {
            if (enumValue.name().equals(value.toString())) {
                return true;
            }
        }
        return false;
    }
}