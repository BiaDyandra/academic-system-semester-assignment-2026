package org.example.academic.system.controller;

import org.example.academic.system.exception.InvalidKeyboardInputException;
import org.example.academic.system.security.Role;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum MenuOption {

    SYSTEM_STATUS("Ver status do sistema", Role.ADMIN, Role.PROFESSOR),
    REGISTER_CLASS("Cadastrar turma", Role.ADMIN),
    REGISTER_ASSESSMENT("Cadastrar avaliacao em uma turma", Role.ADMIN, Role.PROFESSOR),
    LIST_CLASSES_AND_ASSESSMENTS("Listar turmas e avaliacoes", Role.ADMIN, Role.PROFESSOR),
    CONFIGURE_PERSISTENCE_TYPE("Configurar tipo de persistencia", Role.ADMIN),
    SAVE_ACADEMIC_DATA("Salvar dados academicos", Role.ADMIN),
    GENERATE_CLASS_ASSESSMENT_SUMMARY_REPORT("Gerar relatorio resumo de avaliacoes por turma", Role.ADMIN, Role.PROFESSOR),
    GENERATE_ASSESSMENT_WEIGHT_REPORT("Gerar relatorio de peso de avaliacoes", Role.ADMIN, Role.PROFESSOR),
    GENERATE_PERSISTENCE_CONFIGURATION_REPORT("Gerar relatorio de configuracao de persistencia", Role.ADMIN),
    LOGOUT("Logout", Role.ADMIN, Role.PROFESSOR),
    EXIT("Sair", Role.ADMIN, Role.PROFESSOR),
    INVALID("Opcao invalida");

    private final String description;
    private final Set<Role> allowedRoles;

    MenuOption(String description, Role... roles) {
        this.description = description;
        this.allowedRoles = new HashSet<>(Arrays.asList(roles));
    }

    public String getDescription() {
        return description;
    }

    public boolean isAllowedFor(Role role) {
        if (this == INVALID) return false;
        return allowedRoles.contains(role);
    }
}
