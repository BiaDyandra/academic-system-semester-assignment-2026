package org.example.academic.system.model;

import org.example.academic.system.exception.InvalidAcademicClassException;
import org.example.academic.system.exception.InvalidAssessmentException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DomainValidationTest {

    @Test
    void testValidClass() {
        assertDoesNotThrow(() -> new AcademicClass("POO", "Programacao"));
    }

    @Test
    void testInvalidClassBlankCode() {
        assertThrows(InvalidAcademicClassException.class, () -> new AcademicClass("", "Programacao"));
    }

    @Test
    void testInvalidClassBlankTitle() {
        assertThrows(InvalidAcademicClassException.class, () -> new AcademicClass("POO", ""));
    }

    @Test
    void testInvalidAssessmentName() {
        assertThrows(InvalidAssessmentException.class, () -> new Assessment("", 1.0));
    }

    @Test
    void testInvalidAssessmentWeight() {
        assertThrows(InvalidAssessmentException.class, () -> new Assessment("Prova", -1.0));
    }
}
