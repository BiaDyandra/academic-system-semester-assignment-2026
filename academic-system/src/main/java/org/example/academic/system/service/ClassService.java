package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.AcademicClassRepository;

import java.util.List;

public class ClassService {

    private final AcademicClassRepository repository;

    public ClassService(AcademicClassRepository repository) {
        this.repository = repository;
    }

    public void registerClass(String code, String name) {
        AcademicClass academicClass = new AcademicClass(code, name);
        repository.save(academicClass);
    }

    public List<AcademicClass> listClasses() {
        return repository.findAll();
    }
}
