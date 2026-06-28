package org.example.academic.system.exception;

public class AccessDeniedException extends AuthorizationException {

    public AccessDeniedException(String allowedRolesText) {
        super("Acesso negado. Acao permitida apenas para: " + allowedRolesText + ".");
    }
}
