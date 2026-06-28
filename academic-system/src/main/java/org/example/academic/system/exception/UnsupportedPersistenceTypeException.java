package org.example.academic.system.exception;

import org.example.academic.system.repository.PersistenceType;

public class UnsupportedPersistenceTypeException extends AcademicDomainException {

    public UnsupportedPersistenceTypeException(PersistenceType persistenceType) {
        super("O tipo de persistencia " + persistenceType.getDescription() + " ainda nao possui salvamento implementado nesta etapa.");
    }
}
