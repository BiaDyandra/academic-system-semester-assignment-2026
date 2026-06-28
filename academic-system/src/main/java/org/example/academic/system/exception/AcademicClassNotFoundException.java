package org.example.academic.system.exception;

public class AcademicClassNotFoundException extends AcademicDomainException {

    public AcademicClassNotFoundException(String classCode) {
        super("Turma nao encontrada para o codigo: " + classCode + ".");
    }
}
