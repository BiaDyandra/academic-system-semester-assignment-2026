package org.example.academic.system.exception;

public class MissingAuthenticationDataException extends AuthenticationException {

    public MissingAuthenticationDataException(String message) {
        super(message);
    }
}
