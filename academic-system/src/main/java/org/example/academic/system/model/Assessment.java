package org.example.academic.system.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;
import org.example.academic.system.exception.InvalidAssessmentException;
import org.example.academic.system.validation.DomainObjectValidator;

import java.util.Set;

@Getter
@ToString
public class Assessment {

    @NotBlank(message = "O nome da avaliacao nao pode ser vazio.")
    private final String name;

    @Positive(message = "O peso da avaliacao deve ser maior que zero.")
    private final double weight;

    public Assessment(String name, double weight) {
        this.name = name == null ? null : name.trim();
        this.weight = weight;

        validateDomainObject();
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    private void validateDomainObject() {
        Set<ConstraintViolation<Assessment>> violations = DomainObjectValidator.validate(this);

        if (!violations.isEmpty()) {
            throw new InvalidAssessmentException(DomainObjectValidator.formatViolations(violations));
        }
    }
}
