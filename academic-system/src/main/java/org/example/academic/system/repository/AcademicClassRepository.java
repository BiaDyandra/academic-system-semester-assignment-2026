package org.example.academic.system.repository;

import org.example.academic.system.exception.DuplicateAcademicClassException;
import org.example.academic.system.model.AcademicClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcademicClassRepository {

    private final List<AcademicClass> classes = new ArrayList<>();
    private AcademicClass academicClass;

    public void save(AcademicClass academicClass) {
        this.academicClass = academicClass;
        if (existsByCode(academicClass.getCode())) {
            throw new DuplicateAcademicClassException(academicClass.getCode());
        }

        classes.add(academicClass);
    }

    public Optional<AcademicClass> findByCode(String code) {
        if (code == null) {
            return Optional.empty();
        }

        return classes.stream()
                .filter(academicClass -> academicClass.getCode().equalsIgnoreCase(code.trim()))
                .findFirst();
    }

    public List<AcademicClass> findAll() {
        return new ArrayList<>(classes);
    }

    public boolean existsByCode(String code) {
        return findByCode(code).isPresent();
    }
}
