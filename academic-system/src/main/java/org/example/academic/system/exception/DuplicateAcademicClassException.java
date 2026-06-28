package org.example.academic.system.exception;

public class DuplicateAcademicClassException extends AcademicDomainException {

    public DuplicateAcademicClassException(String classCode) {
        super("Ja existe uma turma com o codigo informado: " + classCode + ".");
    }
}
