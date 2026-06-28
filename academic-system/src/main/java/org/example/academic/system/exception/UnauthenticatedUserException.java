package org.example.academic.system.exception;

public class UnauthenticatedUserException extends AuthenticationException {

    public UnauthenticatedUserException() {
        super("Nenhum usuario autenticado.");
    }
}
