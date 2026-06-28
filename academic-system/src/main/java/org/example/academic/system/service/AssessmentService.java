package org.example.academic.system.service;

import org.example.academic.system.exception.AcademicClassNotFoundException;
import org.example.academic.system.exception.InvalidAcademicClassException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.AcademicClassRepository;

public class AssessmentService {

    private final AcademicClassRepository repository;

    public AssessmentService(AcademicClassRepository repository) {
        this.repository = repository;
    }

    public void registerAssessment(String classCode, String assessmentName, double weight) {
        if (classCode == null || classCode.isBlank()) {
            throw new InvalidAcademicClassException("Informe o codigo da turma para cadastrar a avaliacao.");
        }

        AcademicClass academicClass = repository.findByCode(classCode)
                .orElseThrow(() -> new AcademicClassNotFoundException(classCode));

        Assessment assessment = new Assessment(assessmentName, weight);
        academicClass.addAssessment(assessment);
    }
}
