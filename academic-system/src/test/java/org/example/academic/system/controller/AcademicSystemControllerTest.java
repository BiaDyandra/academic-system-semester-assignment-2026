package org.example.academic.system.controller;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.AuthenticationService;
import org.example.academic.system.service.AuthorizationService;
import org.example.academic.system.service.ClassService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AcademicSystemControllerTest {

    @Test
    void shouldDelegateClassRegistrationToClassService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        controller.registerClass("BCC301", "Orientação a Objetos");

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN);
        verify(classService).registerClass("BCC301", "Orientação a Objetos");
    }

    @Test
    void shouldDelegateAssessmentRegistrationToAssessmentService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        controller.registerAssessment("BCC301", "P1", 1.0);

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN, Role.PROFESSOR);
        verify(assessmentService).registerAssessment("BCC301", "P1", 1.0);
    }

    @Test
    void shouldDelegatePersistenceTypeConfigurationToPersistenceService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        controller.configurePersistenceType(PersistenceType.JSON);

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN);
        verify(persistenceService).configurePersistenceType(PersistenceType.JSON);
    }

    @Test
    void shouldDelegateSaveOperationToPersistenceService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        List<AcademicClass> classes = List.of(
                new AcademicClass("BCC301", "Orientação a Objetos")
        );

        Path savedPath = Path.of("academic-data.txt");

        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
        when(classService.listClasses()).thenReturn(classes);
        when(persistenceService.save(classes)).thenReturn(savedPath);
        when(persistenceService.getCurrentPersistenceType()).thenReturn(PersistenceType.TXT);

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        String result = controller.saveAcademicData();

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN);
        verify(classService).listClasses();
        verify(persistenceService).save(classes);

        assertEquals("Dados academicos salvos em TXT: academic-data.txt", result);
    }

    @Test
    void shouldDelegateClassAssessmentSummaryReportToReportService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        List<AcademicClass> classes = List.of(
                new AcademicClass("BCC301", "Orientação a Objetos")
        );

        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
        when(classService.listClasses()).thenReturn(classes);
        when(reportService.generateClassAssessmentSummaryReport(classes, "ADMIN"))
                .thenReturn("Relatorio gerado");

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        String report = controller.generateClassAssessmentSummaryReport();

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN, Role.PROFESSOR);
        verify(classService).listClasses();
        verify(reportService).generateClassAssessmentSummaryReport(classes, "ADMIN");

        assertEquals("Relatorio gerado", report);
    }

    @Test
    void shouldDelegateClassesAndAssessmentsListingToReportService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        List<AcademicClass> classes = List.of(
                new AcademicClass("BCC301", "Orientação a Objetos")
        );

        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
        when(classService.listClasses()).thenReturn(classes);
        when(reportService.generateClassesAndAssessmentsListing(classes))
                .thenReturn("Listagem gerada");

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        String result = controller.listClassesAndAssessments();

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN, Role.PROFESSOR);
        verify(classService).listClasses();
        verify(reportService).generateClassesAndAssessmentsListing(classes);

        assertEquals("Listagem gerada", result);
    }

    @Test
    void shouldDelegateAssessmentWeightReportToReportService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);
        List<AcademicClass> classes = List.of(
                new AcademicClass("BCC301", "Orientação a Objetos")
        );

        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
        when(classService.listClasses()).thenReturn(classes);
        when(reportService.generateAssessmentWeightReport(classes, "ADMIN"))
                .thenReturn("Relatorio de pesos gerado");

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        String result = controller.generateAssessmentWeightReport();

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN, Role.PROFESSOR);
        verify(classService).listClasses();
        verify(reportService).generateAssessmentWeightReport(classes, "ADMIN");

        assertEquals("Relatorio de pesos gerado", result);
    }

    @Test
    void shouldDelegatePersistenceConfigurationReportToReportService() {
        ClassService classService = mock(ClassService.class);
        AssessmentService assessmentService = mock(AssessmentService.class);
        PersistenceService persistenceService = mock(PersistenceService.class);
        ReportService reportService = mock(ReportService.class);
        AuthenticationService authenticationService = mock(AuthenticationService.class);
        AuthorizationService authorizationService = mock(AuthorizationService.class);

        User admin = new User("admin", "admin123", Role.ADMIN);

        when(authenticationService.getAuthenticatedUser()).thenReturn(admin);
        when(persistenceService.getCurrentPersistenceType()).thenReturn(PersistenceType.JSON);
        when(reportService.generatePersistenceConfigurationReport(PersistenceType.JSON, "ADMIN"))
                .thenReturn("Relatorio de configuracao de persistencia gerado");

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        String result = controller.generatePersistenceConfigurationReport();

        verify(authorizationService).requireAnyRole(admin, Role.ADMIN);
        verify(persistenceService).getCurrentPersistenceType();
        verify(reportService).generatePersistenceConfigurationReport(PersistenceType.JSON, "ADMIN");

        assertEquals("Relatorio de configuracao de persistencia gerado", result);
    }

}
