package org.example.academic.system.service;

import org.example.academic.system.exception.InvalidCredentialsException;
import org.example.academic.system.exception.MissingAuthenticationDataException;
import org.example.academic.system.exception.UnauthenticatedUserException;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final Map<String, User> usersByUsername = new HashMap<>();
    private User authenticatedUser;

    public AuthenticationService() {
        registerDefaultUsers();
    }

    public User authenticate(String username, String password) {
        if (username == null || username.isBlank()) {
            LOGGER.warn("Falha na tentativa de login. Usuario: desconhecido.");
            throw new MissingAuthenticationDataException("Informe o usuario.");
        }

        String normalizedUsername = username.trim();

        if (password == null || password.isBlank()) {
            LOGGER.warn("Falha na tentativa de login. Usuario: {}.", normalizedUsername);
            throw new MissingAuthenticationDataException("Informe a senha.");
        }

        User user = usersByUsername.get(normalizedUsername.toLowerCase());

        if (user == null || !user.getPassword().equals(password)) {
            LOGGER.warn("Falha na tentativa de login. Usuario: {}.", normalizedUsername);
            throw new InvalidCredentialsException();
        }

        authenticatedUser = user;
        LOGGER.info("Login realizado com sucesso. Usuario: {} | Perfil: {}", user.getUsername(), user.getRole());

        return user;
    }

    public void logout() {
        if (authenticatedUser != null) {
            LOGGER.info("Logout realizado. Usuario: {}", authenticatedUser.getUsername());
        } else {
            LOGGER.info("Logout solicitado sem usuario autenticado.");
        }

        authenticatedUser = null;
    }

    public User getAuthenticatedUser() {
        if (authenticatedUser == null) {
            throw new UnauthenticatedUserException();
        }

        return authenticatedUser;
    }

    public boolean isAuthenticated() {
        return authenticatedUser != null;
    }

    private void registerDefaultUsers() {
        addUser(new User("admin", "admin123", Role.ADMIN));
        addUser(new User("professor", "prof123", Role.PROFESSOR));
    }

    private void addUser(User user) {
        usersByUsername.put(user.getUsername().toLowerCase(), user);
    }
}
