package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.PersistenceType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportServiceTest {

    @Test
    void shouldGenerateEmptyClassAssessmentSummaryReport() {
        ReportService reportService = new ReportService();

        String report = reportService.generateClassAssessmentSummaryReport(
                List.of(),
                "ADMIN"
        );

        assertTrue(report.contains("Relatorio Resumo de Avaliacoes por Turma"));
        assertTrue(report.contains("Nenhuma turma cadastrada"));
    }

    @Test
    void shouldGenerateClassAssessmentSummaryReportWithClassAndAssessment() {
        ReportService reportService = new ReportService();

        AcademicClass academicClass = new AcademicClass("BCC301", "Orientação a Objetos");
        academicClass.addAssessment(new Assessment("P1", 1.0));

        String report = reportService.generateClassAssessmentSummaryReport(
                List.of(academicClass),
                "ADMIN"
        );

        assertTrue(report.contains("Relatorio Resumo de Avaliacoes por Turma"));
        assertTrue(report.contains("Total de turmas cadastradas: 1"));
        assertTrue(report.contains("Total de avaliacoes cadastradas: 1"));
        assertTrue(report.contains("BCC301"));
        assertTrue(report.contains("Orientação a Objetos"));
        assertTrue(report.contains("P1"));
        assertTrue(report.contains("1.00"));
    }

    @Test
    void shouldGenerateEmptyClassesAndAssessmentsListing() {
        ReportService reportService = new ReportService();

        String listing = reportService.generateClassesAndAssessmentsListing(List.of());

        assertTrue(listing.contains("Nenhuma turma cadastrada"));
    }

    @Test
    void shouldGenerateClassesAndAssessmentsListingWithClassAndAssessment() {
        ReportService reportService = new ReportService();

        AcademicClass academicClass = new AcademicClass("BCC302", "Estruturas de Dados");
        academicClass.addAssessment(new Assessment("Trabalho", 0.8));

        String listing = reportService.generateClassesAndAssessmentsListing(
                List.of(academicClass)
        );

        assertTrue(listing.contains("Turma: BCC302 - Estruturas de Dados"));
        assertTrue(listing.contains("Trabalho"));
        assertTrue(listing.contains("0.80"));
    }

    @Test
    void shouldGenerateAssessmentWeightReport() {
        ReportService reportService = new ReportService();

        AcademicClass academicClass = new AcademicClass("BCC303", "Banco de Dados");
        academicClass.addAssessment(new Assessment("P1", 0.4));
        academicClass.addAssessment(new Assessment("P2", 0.6));

        String report = reportService.generateAssessmentWeightReport(
                List.of(academicClass),
                "ADMIN"
        );

        assertTrue(report.contains("Relatorio de Peso de Avaliacoes"));
        assertTrue(report.contains("BCC303"));
        assertTrue(report.contains("Banco de Dados"));
        assertTrue(report.contains("Peso total: 1.00"));
        assertTrue(report.contains("VALIDA"));
    }

    @Test
    void shouldGeneratePersistenceConfigurationReport() {
        ReportService reportService = new ReportService();

        String report = reportService.generatePersistenceConfigurationReport(
                PersistenceType.JSON,
                "ADMIN"
        );

        assertTrue(report.contains("Relatorio de Configuracao de Persistencia"));
        assertTrue(report.contains("JSON"));
    }
}
