package org.example.academic.system.exception;

public class InvalidCredentialsException extends AuthenticationException {

    public InvalidCredentialsException() {
        super("Usuario ou senha invalidos.");
    }
}
