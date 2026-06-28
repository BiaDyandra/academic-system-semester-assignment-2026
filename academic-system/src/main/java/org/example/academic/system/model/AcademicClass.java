package org.example.academic.system.model;

import jakarta.validation.Valid;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;
import org.example.academic.system.exception.InvalidAcademicClassException;
import org.example.academic.system.exception.InvalidAssessmentException;
import org.example.academic.system.validation.DomainObjectValidator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ToString
public class AcademicClass {

    @Getter
    @NotBlank(message = "O codigo da turma nao pode ser vazio.")
    private final String code;

    @Getter
    @NotBlank(message = "O nome da turma nao pode ser vazio.")
    private final String name;

    @Valid
    @ToString.Exclude
    private final List<Assessment> assessments;

    public AcademicClass(String code, String name) {
        this.code = code == null ? null : code.trim();
        this.name = name == null ? null : name.trim();
        this.assessments = new ArrayList<>();

        validateDomainObject();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<Assessment> getAssessments() {
        return Collections.unmodifiableList(assessments);
    }

    public void addAssessment(Assessment assessment) {
        if (assessment == null) {
            throw new InvalidAssessmentException("A avaliacao nao pode ser nula.");
        }

        assessments.add(assessment);
    }

    private void validateDomainObject() {
        Set<ConstraintViolation<AcademicClass>> violations = DomainObjectValidator.validate(this);

        if (!violations.isEmpty()) {
            throw new InvalidAcademicClassException(DomainObjectValidator.formatViolations(violations));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcademicClass that = (AcademicClass) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
