package com.example.hotelreservationsystem.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InputValidator {

    private static Validator validator;

    @Autowired
    public InputValidator(Validator validator) {
        this.validator = validator;
    }

    public static String validate(Object dto) {
        Set<ConstraintViolation<Object>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<Object> violation : violations) {
                builder.append(violation.getMessage())
                        .append(System.lineSeparator());
            }
            return builder.toString().trim();
        }
        return null;
    }
}
