package org.example.academic.system.service;

import org.example.academic.system.exception.AcademicDomainException;
import org.example.academic.system.exception.DuplicateAcademicClassException;
import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.AcademicClassRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassServiceTest {

    @Test
    void shouldRegisterValidClass() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);

        classService.registerClass("BCC301", "Orientação a Objetos");

        List<AcademicClass> classes = classService.listClasses();

        assertEquals(1, classes.size());
        assertEquals("BCC301", classes.get(0).getCode());
        assertEquals("Orientação a Objetos", classes.get(0).getName());
    }

    @Test
    void registeredClassMustBeStoredInRepository() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);

        classService.registerClass("BCC302", "Estruturas de Dados");

        assertTrue(repository.existsByCode("BCC302"));
    }

    @Test
    void shouldThrowExceptionWhenClassCodeIsBlank() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);

        assertThrows(
                AcademicDomainException.class,
                () -> classService.registerClass("", "Orientação a Objetos")
        );
    }

    @Test
    void shouldThrowExceptionWhenClassNameIsBlank() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);

        assertThrows(
                AcademicDomainException.class,
                () -> classService.registerClass("BCC301", "")
        );
    }

    @Test
    void shouldThrowExceptionWhenClassCodeAlreadyExists() {
        AcademicClassRepository repository = new AcademicClassRepository();
        ClassService classService = new ClassService(repository);

        classService.registerClass("BCC301", "Orientação a Objetos");

        assertThrows(
                DuplicateAcademicClassException.class,
                () -> classService.registerClass("BCC301", "Outra Turma")
        );
    }
}
