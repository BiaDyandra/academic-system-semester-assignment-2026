package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TxtAcademicDataRepository {

    private static final String DEFAULT_FILE_PATH = "data/academic-data.txt";

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
            throw new IllegalStateException("Nao foi possivel salvar os dados em TXT.", e);
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
            writer.write("===== Dados Academicos =====");
            writer.newLine();

            if (classes.isEmpty()) {
                writer.write("Nenhuma turma cadastrada.");
                writer.newLine();
                return;
            }

            for (AcademicClass academicClass : classes) {
                writer.write("Turma: " + academicClass.getCode() + " - " + academicClass.getName());
                writer.newLine();

                if (academicClass.getAssessments().isEmpty()) {
                    writer.write("  Nenhuma avaliacao cadastrada.");
                    writer.newLine();
                } else {
                    for (Assessment assessment : academicClass.getAssessments()) {
                        writer.write("  Avaliacao: " + assessment.getName() + " | Peso: " + assessment.getWeight());
                        writer.newLine();
                    }
                }

                writer.newLine();
            }
        }
    }
}
