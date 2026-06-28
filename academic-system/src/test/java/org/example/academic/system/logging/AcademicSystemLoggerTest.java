package org.example.academic.system.logging;

import org.example.academic.system.exception.InvalidCredentialsException;
import org.example.academic.system.security.User;
import org.example.academic.system.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AcademicSystemLoggerTest {

    @Test
    void shouldCreateLoggerInstanceSuccessfully() {
        Logger logger = LoggerFactory.getLogger("AcademicSystem");

        assertNotNull(logger);
    }

    @Test
    void shouldWriteAuditLogsWithoutThrowingExceptions() {
        Logger logger = LoggerFactory.getLogger("AcademicSystemTest");

        assertDoesNotThrow(() -> {
            logger.info("Login realizado com sucesso. Usuario: {}", "admin");
            logger.warn("Falha na tentativa de login. Usuario: {}", "admin");
            logger.info("Logout realizado. Usuario: {}", "admin");
            logger.warn("Falha de autorizacao. Usuario: {} | Perfil atual: {} | Perfis permitidos: {}", "professor", "PROFESSOR", "ADMIN");
            logger.info("Dados academicos persistidos com sucesso. Tipo: {} | Turmas: {} | Arquivo: {}", "TXT", 1, "academic-data.txt");
            logger.info("Relatorio gerado. Relatorio: {} | Perfil do usuario autenticado: {}", "Resumo de avaliacoes por turma", "ADMIN");
        });
    }

    @Test
    void loggingShouldNotChangeSuccessfulAuthenticationBehavior() {
        AuthenticationService authenticationService = new AuthenticationService();

        User user = assertDoesNotThrow(() ->
                authenticationService.authenticate("admin", "admin123")
        );

        assertEquals("admin", user.getUsername());
        assertTrue(authenticationService.isAuthenticated());
    }

    @Test
    void loggingShouldNotChangeFailedAuthenticationBehavior() {
        AuthenticationService authenticationService = new AuthenticationService();

        assertThrows(
                InvalidCredentialsException.class,
                () -> authenticationService.authenticate("admin", "senhaErrada")
        );

        assertFalse(authenticationService.isAuthenticated());
    }

    @Test
    void loggingShouldNotChangeLogoutBehavior() {
        AuthenticationService authenticationService = new AuthenticationService();

        authenticationService.authenticate("admin", "admin123");

        assertTrue(authenticationService.isAuthenticated());

        assertDoesNotThrow(authenticationService::logout);

        assertFalse(authenticationService.isAuthenticated());
    }
}
