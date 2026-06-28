package org.example.academic.system.service;

import org.example.academic.system.exception.AcademicClassNotFoundException;
import org.example.academic.system.exception.InvalidAcademicClassException;
import org.example.academic.system.exception.InvalidAssessmentException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.AcademicClassRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssessmentServiceTest {

    @Test
    void shouldRegisterAssessmentInExistingClass() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);
        AssessmentService assessmentService = new AssessmentService(repository);

        classService.registerClass("BCC301", "Orientação a Objetos");

        assessmentService.registerAssessment("BCC301", "P1", 1.0);

        AcademicClass academicClass = repository.findByCode("BCC301").orElseThrow();
        Assessment assessment = academicClass.getAssessments().get(0);

        assertEquals(1, academicClass.getAssessments().size());
        assertEquals("P1", assessment.getName());
        assertEquals(1.0, assessment.getWeight());
    }

    @Test
    void shouldThrowExceptionWhenClassCodeIsBlank() {
        AcademicClassRepository repository = new AcademicClassRepository();
        AssessmentService assessmentService = new AssessmentService(repository);

        assertThrows(
                InvalidAcademicClassException.class,
                () -> assessmentService.registerAssessment("", "P1", 1.0)
        );
    }

    @Test
    void shouldThrowExceptionWhenClassDoesNotExist() {
        AcademicClassRepository repository = new AcademicClassRepository();
        AssessmentService assessmentService = new AssessmentService(repository);

        assertThrows(
                AcademicClassNotFoundException.class,
                () -> assessmentService.registerAssessment("BCC999", "P1", 1.0)
        );
    }

    @Test
    void shouldThrowExceptionWhenAssessmentNameIsBlank() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);
        AssessmentService assessmentService = new AssessmentService(repository);

        classService.registerClass("BCC301", "Orientação a Objetos");

        assertThrows(
                InvalidAssessmentException.class,
                () -> assessmentService.registerAssessment("BCC301", "", 1.0)
        );
    }

    @Test
    void shouldThrowExceptionWhenAssessmentWeightIsZero() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);
        AssessmentService assessmentService = new AssessmentService(repository);

        classService.registerClass("BCC301", "Orientação a Objetos");

        assertThrows(
                InvalidAssessmentException.class,
                () -> assessmentService.registerAssessment("BCC301", "P1", 0)
        );
    }

    @Test
    void shouldThrowExceptionWhenAssessmentWeightIsNegative() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);
        AssessmentService assessmentService = new AssessmentService(repository);

        classService.registerClass("BCC301", "Orientação a Objetos");

        assertThrows(
                InvalidAssessmentException.class,
                () -> assessmentService.registerAssessment("BCC301", "P1", -0.5)
        );
    }
}
