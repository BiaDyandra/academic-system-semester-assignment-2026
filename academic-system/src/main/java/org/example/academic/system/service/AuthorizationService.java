package org.example.academic.system.service;

import org.example.academic.system.exception.AccessDeniedException;
import org.example.academic.system.exception.UnauthenticatedUserException;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AuthorizationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);

    public void requireAnyRole(User user, Role... allowedRoles) {
        String allowedRolesText = formatAllowedRoles(allowedRoles);

        if (user == null) {
            LOGGER.warn("Falha de autorizacao. Usuario: desconhecido | Perfil atual: sem autenticacao | Perfis permitidos: {}", allowedRolesText);
            throw new UnauthenticatedUserException();
        }

        boolean authorized = Arrays.stream(allowedRoles)
                .anyMatch(role -> role == user.getRole());

        if (!authorized) {
            LOGGER.warn(
                    "Falha de autorizacao. Usuario: {} | Perfil atual: {} | Perfis permitidos: {}",
                    user.getUsername(),
                    user.getRole(),
                    allowedRolesText
            );

            throw new AccessDeniedException(allowedRolesText);
        }
    }

    private String formatAllowedRoles(Role... allowedRoles) {
        return Arrays.stream(allowedRoles)
                .map(Role::name)
                .collect(Collectors.joining(", "));
    }
}
