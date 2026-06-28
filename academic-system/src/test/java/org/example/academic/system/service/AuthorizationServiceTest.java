package org.example.academic.system.service;

import org.example.academic.system.exception.AccessDeniedException;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        authorizationService = new AuthorizationService();
    }

    @Test
    void testAuthorizedAccess() {
        User adminUser = new User("admin", "pass", Role.ADMIN);
        assertDoesNotThrow(() -> authorizationService.requireAnyRole(adminUser, Role.ADMIN));
    }

    @Test
    void testUnauthorizedAccess() {
        User profUser = new User("prof", "pass", Role.PROFESSOR);
        assertThrows(AccessDeniedException.class, () -> authorizationService.requireAnyRole(profUser, Role.ADMIN));
    }
}
