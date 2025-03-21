package com.modulo.pix.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataValidator implements ConstraintValidator<ValidateDate, Object> {

    @Override
    public boolean isValid(Object data, ConstraintValidatorContext context) {
        if (data == null) return true;

        try {
            if (data instanceof String) {

                LocalDateTime.parse((String) data, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                return true;
            } else if (data instanceof LocalDateTime) {

                return true;
            }
        } catch (DateTimeParseException e) {
            return false;
        }

        return false;
    }
}
