package org.example.academic.system.controller;

import org.example.academic.system.controller.input.MenuInput;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.AuthenticationService;
import org.example.academic.system.service.AuthorizationService;
import org.example.academic.system.service.ClassService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;

import java.nio.file.Path;

public class AcademicSystemController {

    private final ClassService classService;
    private final AssessmentService assessmentService;
    private final PersistenceService persistenceService;
    private final ReportService reportService;
    private final AuthenticationService authenticationService;
    private final AuthorizationService authorizationService;

    public AcademicSystemController(
            ClassService classService,
            AssessmentService assessmentService,
            PersistenceService persistenceService,
            ReportService reportService,
            AuthenticationService authenticationService,
            AuthorizationService authorizationService
    ) {
        this.classService = classService;
        this.assessmentService = assessmentService;
        this.persistenceService = persistenceService;
        this.reportService = reportService;
        this.authenticationService = authenticationService;
        this.authorizationService = authorizationService;
    }

    public String login(String username, String password) {
        User user = authenticationService.authenticate(username, password);
        return "Login realizado com sucesso. Usuario: " + user.getUsername() + " | Perfil: " + user.getRole();
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public String getLoggedUserDescription() {
        User user = authenticationService.getAuthenticatedUser();
        return user.getUsername() + " (" + user.getRole() + ")";
    }

    public String getCurrentPersistenceTypeDescription() {
        return persistenceService.getCurrentPersistenceType().getDescription();
    }

    public void logout() {
        authenticationService.logout();
    }

    public String executeMenuOption(MenuOption option, MenuInput input) {
        return switch (option) {
            case SYSTEM_STATUS -> getSystemStatus();
            case REGISTER_CLASS -> registerClassFromMenu(input);
            case REGISTER_ASSESSMENT -> registerAssessmentFromMenu(input);
            case LIST_CLASSES_AND_ASSESSMENTS -> listClassesAndAssessments();
            case CONFIGURE_PERSISTENCE_TYPE -> configurePersistenceTypeFromMenu(input);
            case SAVE_ACADEMIC_DATA -> saveAcademicData();
            case GENERATE_CLASS_ASSESSMENT_SUMMARY_REPORT -> generateClassAssessmentSummaryReport();
            case GENERATE_ASSESSMENT_WEIGHT_REPORT -> generateAssessmentWeightReport();
            case GENERATE_PERSISTENCE_CONFIGURATION_REPORT -> generatePersistenceConfigurationReport();
            case LOGOUT -> {
                logout();
                yield "Logout realizado com sucesso.";
            }
            case EXIT -> exitSystem();
            case INVALID -> "Opcao invalida. Tente novamente.";
        };
    }

    public String getSystemStatus() {
        authorize(Role.ADMIN, Role.PROFESSOR);
        return "Sistema Academico em execucao. Tipo de persistencia atual: "
                + getCurrentPersistenceTypeDescription()
                + ".";
    }

    public String registerClassFromMenu(MenuInput input) {
        String code = input.readText("Codigo da turma: ");
        String name = input.readText("Nome da turma: ");

        registerClass(code, name);
        return "Turma cadastrada com sucesso.";
    }

    public void registerClass(String code, String name) {
        authorize(Role.ADMIN);
        classService.registerClass(code, name);
    }

    public String registerAssessmentFromMenu(MenuInput input) {
        String classCode = input.readText("Codigo da turma: ");
        String assessmentName = input.readText("Nome da avaliacao: ");
        double weight = input.readDouble("Peso da avaliacao: ");

        registerAssessment(classCode, assessmentName, weight);
        return "Avaliacao cadastrada com sucesso.";
    }

    public void registerAssessment(String classCode, String assessmentName, double weight) {
        authorize(Role.ADMIN, Role.PROFESSOR);
        assessmentService.registerAssessment(classCode, assessmentName, weight);
    }

    public String listClassesAndAssessments() {
        authorize(Role.ADMIN, Role.PROFESSOR);
        return reportService.generateClassesAndAssessmentsListing(classService.listClasses());
    }

    public String configurePersistenceTypeFromMenu(MenuInput input) {
        System.out.println("===== Tipos de Persistencia =====");
        System.out.print(PersistenceType.menuOptionsText());

        int option = input.readInt("Escolha o tipo de persistencia: ");
        PersistenceType persistenceType = PersistenceType.fromCode(option);

        configurePersistenceType(persistenceType);
        return "Tipo de persistencia configurado para: " + persistenceType.getDescription() + ".";
    }

    public void configurePersistenceType(PersistenceType persistenceType) {
        authorize(Role.ADMIN);
        persistenceService.configurePersistenceType(persistenceType);
    }

    public String saveAcademicData() {
        authorize(Role.ADMIN);
        Path savedFile = persistenceService.save(classService.listClasses());
        return "Dados academicos salvos em " + getCurrentPersistenceTypeDescription() + ": " + savedFile;
    }

    public String generateClassAssessmentSummaryReport() {
        authorize(Role.ADMIN, Role.PROFESSOR);
        Role userRole = authenticationService.getAuthenticatedUser().getRole();
        return reportService.generateClassAssessmentSummaryReport(classService.listClasses(), userRole.name());
    }

    public String generateAssessmentWeightReport() {
        authorize(Role.ADMIN, Role.PROFESSOR);
        Role userRole = authenticationService.getAuthenticatedUser().getRole();
        return reportService.generateAssessmentWeightReport(classService.listClasses(), userRole.name());
    }

    public String generatePersistenceConfigurationReport() {
        authorize(Role.ADMIN);
        Role userRole = authenticationService.getAuthenticatedUser().getRole();
        return reportService.generatePersistenceConfigurationReport(
                persistenceService.getCurrentPersistenceType(),
                userRole.name()
        );
    }

    public String exitSystem() {
        return "Encerrando o Sistema Academico...";
    }

    private void authorize(Role... roles) {
        User authenticatedUser = authenticationService.getAuthenticatedUser();
        authorizationService.requireAnyRole(authenticatedUser, roles);
    }
}
