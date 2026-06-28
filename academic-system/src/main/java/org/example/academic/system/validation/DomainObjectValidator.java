package org.example.academic.system.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;
import java.util.stream.Collectors;

public final class DomainObjectValidator {

    private static final ValidatorFactory VALIDATOR_FACTORY = Validation.buildDefaultValidatorFactory();
    private static final Validator VALIDATOR = VALIDATOR_FACTORY.getValidator();

    private DomainObjectValidator() {
    }

    public static <T> Set<ConstraintViolation<T>> validate(T object) {
        return VALIDATOR.validate(object);
    }

    public static <T> boolean isValid(T object) {
        return validate(object).isEmpty();
    }

    public static <T> String formatViolations(Set<ConstraintViolation<T>> violations) {
        return violations.stream()
                .map(violation -> violation.getMessage())
                .collect(Collectors.joining(" "));
    }
}
