package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.example.academic.system.repository.PersistenceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;

public class ReportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportService.class);

    public String generateClassAssessmentSummaryReport(List<AcademicClass> classes, String userRole) {
        logReportGeneration("Resumo de avaliacoes por turma", userRole);
        return buildClassAssessmentSummaryReport(classes);
    }

    public String generateClassAssessmentSummaryReport(List<AcademicClass> classes) {
        return buildClassAssessmentSummaryReport(classes);
    }

    public String generateAssessmentWeightReport(List<AcademicClass> classes, String userRole) {
        logReportGeneration("Relatorio de peso de avaliacoes", userRole);
        return buildAssessmentWeightReport(classes);
    }

    public String generateAssessmentWeightReport(List<AcademicClass> classes) {
        return buildAssessmentWeightReport(classes);
    }

    public String generatePersistenceConfigurationReport(PersistenceType currentPersistenceType, String userRole) {
        logReportGeneration("Relatorio de configuracao de persistencia", userRole);
        return buildPersistenceConfigurationReport(currentPersistenceType);
    }

    public String generatePersistenceConfigurationReport(PersistenceType currentPersistenceType) {
        return buildPersistenceConfigurationReport(currentPersistenceType);
    }

    public String generateClassesAndAssessmentsListing(List<AcademicClass> classes) {
        if (classes == null || classes.isEmpty()) {
            return "Nenhuma turma cadastrada.";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("===== Turmas e Avaliacoes =====").append(System.lineSeparator());

        for (AcademicClass academicClass : classes) {
            builder.append("Turma: ")
                    .append(academicClass.getCode())
                    .append(" - ")
                    .append(academicClass.getName())
                    .append(System.lineSeparator());

            if (academicClass.getAssessments().isEmpty()) {
                builder.append("  Nenhuma avaliacao cadastrada.").append(System.lineSeparator());
            } else {
                for (Assessment assessment : academicClass.getAssessments()) {
                    builder.append("  - ")
                            .append(assessment.getName())
                            .append(" | Peso: ")
                            .append(formatWeight(assessment.getWeight()))
                            .append(System.lineSeparator());
                }
            }
        }

        return builder.toString();
    }

    private String buildClassAssessmentSummaryReport(List<AcademicClass> classes) {
        StringBuilder report = new StringBuilder();

        report.append("===== Relatorio Resumo de Avaliacoes por Turma =====")
                .append(System.lineSeparator());

        if (classes == null || classes.isEmpty()) {
            report.append("Nenhuma turma cadastrada.").append(System.lineSeparator());
            return report.toString();
        }

        int totalAssessments = countAssessments(classes);

        report.append("Total de turmas cadastradas: ")
                .append(classes.size())
                .append(System.lineSeparator());
        report.append("Total de avaliacoes cadastradas: ")
                .append(totalAssessments)
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        for (AcademicClass academicClass : classes) {
            List<Assessment> assessments = academicClass.getAssessments();
            double totalWeight = calculateTotalWeight(assessments);

            report.append("Turma: ")
                    .append(academicClass.getCode())
                    .append(" - ")
                    .append(academicClass.getName())
                    .append(System.lineSeparator());
            report.append("Quantidade de avaliacoes: ")
                    .append(assessments.size())
                    .append(System.lineSeparator());
            report.append("Peso total: ")
                    .append(formatWeight(totalWeight))
                    .append(System.lineSeparator());

            if (assessments.isEmpty()) {
                report.append("Avaliacoes: nenhuma avaliacao cadastrada.")
                        .append(System.lineSeparator());
            } else {
                report.append("Avaliacoes:").append(System.lineSeparator());

                for (int i = 0; i < assessments.size(); i++) {
                    Assessment assessment = assessments.get(i);
                    report.append("  ")
                            .append(i + 1)
                            .append(". ")
                            .append(assessment.getName())
                            .append(" | Peso: ")
                            .append(formatWeight(assessment.getWeight()))
                            .append(System.lineSeparator());
                }
            }

            report.append(System.lineSeparator());
        }

        return report.toString();
    }

    private String buildAssessmentWeightReport(List<AcademicClass> classes) {
        StringBuilder report = new StringBuilder();

        report.append("===== Relatorio de Peso de Avaliacoes =====").append(System.lineSeparator());

        if (classes == null || classes.isEmpty()) {
            report.append("Nenhuma turma cadastrada.").append(System.lineSeparator());
            return report.toString();
        }

        for (AcademicClass academicClass : classes) {
            report.append("Turma: ")
                    .append(academicClass.getCode())
                    .append(" - ")
                    .append(academicClass.getName())
                    .append(System.lineSeparator());

            List<Assessment> assessments = academicClass.getAssessments();
            double totalWeight = calculateTotalWeight(assessments);

            report.append("Peso total: ").append(formatWeight(totalWeight)).append(System.lineSeparator());

            if (assessments.isEmpty()) {
                report.append("Total de peso exibido como 0.0 pois nao ha avaliacoes cadastradas.");
            } else if (Math.abs(totalWeight - 1.0) < 0.001) {
                report.append("Composicao de avaliacoes VALIDA (peso total = 1.0).");
            } else {
                report.append("Composicao de avaliacoes INVALIDA (peso total diferente de 1.0).");
            }

            report.append(System.lineSeparator()).append(System.lineSeparator());
        }

        return report.toString();
    }

    private String buildPersistenceConfigurationReport(PersistenceType currentPersistenceType) {
        StringBuilder report = new StringBuilder();

        report.append("===== Relatorio de Configuracao de Persistencia =====").append(System.lineSeparator());
        report.append("Mecanismo de persistencia atual: ")
                .append(currentPersistenceType.name())
                .append(" (")
                .append(currentPersistenceType.getDescription())
                .append(")")
                .append(System.lineSeparator());

        return report.toString();
    }

    private void logReportGeneration(String reportName, String userRole) {
        LOGGER.info(
                "Relatorio gerado. Relatorio: {} | Perfil do usuario autenticado: {}",
                reportName,
                userRole == null || userRole.isBlank() ? "desconhecido" : userRole.trim()
        );
    }

    private int countAssessments(List<AcademicClass> classes) {
        return classes.stream()
                .mapToInt(academicClass -> academicClass.getAssessments().size())
                .sum();
    }

    private double calculateTotalWeight(List<Assessment> assessments) {
        return assessments.stream()
                .mapToDouble(Assessment::getWeight)
                .sum();
    }

    private String formatWeight(double weight) {
        return String.format(Locale.US, "%.2f", weight);
    }
}
