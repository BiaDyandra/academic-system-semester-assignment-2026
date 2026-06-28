package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class JsonAcademicDataRepository {

    private static final String DEFAULT_FILE_PATH = "data/academic-data.json";

    public Path save(List<AcademicClass> classes) {
        return save(classes, DEFAULT_FILE_PATH);
    }

    public Path save(List<AcademicClass> classes, String filePath) {
        Path path = Paths.get(filePath);

        try {
            createParentDirectories(path);
            writeClassesToFile(classes, path);
            return path;
        } catch (IOException e) {
            throw new IllegalStateException("Nao foi possivel salvar os dados em JSON.", e);
        }
    }

    private void createParentDirectories(Path path) throws IOException {
        Path parent = path.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    private void writeClassesToFile(List<AcademicClass> classes, Path path) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(toJson(classes));
        }
    }

    private String toJson(List<AcademicClass> classes) {
        StringBuilder builder = new StringBuilder();
        builder.append("{").append(System.lineSeparator());
        builder.append("  \"classes\": [").append(System.lineSeparator());

        for (int i = 0; i < classes.size(); i++) {
            AcademicClass academicClass = classes.get(i);

            builder.append("    {").append(System.lineSeparator());
            builder.append("      \"code\": \"").append(escapeJson(academicClass.getCode())).append("\",").append(System.lineSeparator());
            builder.append("      \"name\": \"").append(escapeJson(academicClass.getName())).append("\",").append(System.lineSeparator());
            builder.append("      \"assessments\": [").append(System.lineSeparator());

            List<Assessment> assessments = academicClass.getAssessments();
            for (int j = 0; j < assessments.size(); j++) {
                Assessment assessment = assessments.get(j);

                builder.append("        {").append(System.lineSeparator());
                builder.append("          \"name\": \"").append(escapeJson(assessment.getName())).append("\",").append(System.lineSeparator());
                builder.append("          \"weight\": ").append(assessment.getWeight()).append(System.lineSeparator());
                builder.append("        }");

                if (j < assessments.size() - 1) {
                    builder.append(",");
                }

                builder.append(System.lineSeparator());
            }

            builder.append("      ]").append(System.lineSeparator());
            builder.append("    }");

            if (i < classes.size() - 1) {
                builder.append(",");
            }

            builder.append(System.lineSeparator());
        }

        builder.append("  ]").append(System.lineSeparator());
        builder.append("}").append(System.lineSeparator());
        return builder.toString();
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
