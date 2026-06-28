package org.example.academic.system.service;

import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.exception.InvalidCredentialsException;
import org.example.academic.system.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService();
    }

    @Test
    void testValidAuthentication() {
        User user = authenticationService.authenticate("admin", "admin123");
        assertNotNull(user);
        assertEquals("admin", user.getUsername());
    }

    @Test
    void testInvalidUsername() {
        assertThrows(AuthenticationException.class, () -> authenticationService.authenticate("invalid", "admin123"));
    }

    @Test
    void testInvalidPassword() {
        assertThrows(InvalidCredentialsException.class, () -> authenticationService.authenticate("admin", "wrong"));
    }
}
