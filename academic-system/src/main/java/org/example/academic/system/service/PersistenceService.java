package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.JsonAcademicDataRepository;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.repository.TxtAcademicDataRepository;
import org.example.academic.system.repository.XmlAcademicDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;

public class PersistenceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceService.class);

    private final TxtAcademicDataRepository txtRepository;
    private final XmlAcademicDataRepository xmlRepository;
    private final JsonAcademicDataRepository jsonRepository;
    private PersistenceType currentPersistenceType;

    public PersistenceService(
            TxtAcademicDataRepository txtRepository,
            XmlAcademicDataRepository xmlRepository,
            JsonAcademicDataRepository jsonRepository
    ) {
        this.txtRepository = txtRepository;
        this.xmlRepository = xmlRepository;
        this.jsonRepository = jsonRepository;
        this.currentPersistenceType = PersistenceType.TXT;
    }

    public PersistenceType getCurrentPersistenceType() {
        return currentPersistenceType;
    }

    public void configurePersistenceType(PersistenceType persistenceType) {
        this.currentPersistenceType = persistenceType;
        LOGGER.info("Tipo de persistencia configurado para: {}", persistenceType.getDescription());
    }

    public Path save(List<AcademicClass> classes) {
        return switch (currentPersistenceType) {
            case TXT -> saveToTxt(classes);
            case XML -> saveToXml(classes);
            case JSON -> saveToJson(classes);
        };
    }

    public Path saveToTxt(List<AcademicClass> classes) {
        try {
            Path path = txtRepository.save(classes);
            logPersistenceSuccess(PersistenceType.TXT, classes, path);
            return path;
        } catch (RuntimeException exception) {
            logPersistenceFailure("salvar dados academicos", PersistenceType.TXT, exception);
            throw exception;
        }
    }

    public Path saveToXml(List<AcademicClass> classes) {
        try {
            Path path = xmlRepository.save(classes);
            logPersistenceSuccess(PersistenceType.XML, classes, path);
            return path;
        } catch (RuntimeException exception) {
            logPersistenceFailure("salvar dados academicos", PersistenceType.XML, exception);
            throw exception;
        }
    }

    public Path saveToJson(List<AcademicClass> classes) {
        try {
            Path path = jsonRepository.save(classes);
            logPersistenceSuccess(PersistenceType.JSON, classes, path);
            return path;
        } catch (RuntimeException exception) {
            logPersistenceFailure("salvar dados academicos", PersistenceType.JSON, exception);
            throw exception;
        }
    }

    private void logPersistenceSuccess(PersistenceType persistenceType, List<AcademicClass> classes, Path path) {
        LOGGER.info(
                "Dados academicos persistidos com sucesso. Tipo: {} | Turmas: {} | Arquivo: {}",
                persistenceType.getDescription(),
                countClasses(classes),
                path
        );
    }

    private void logPersistenceFailure(String operation, PersistenceType persistenceType, RuntimeException exception) {
        LOGGER.warn(
                "Falha na operacao de persistencia. Operacao: {} | Tipo: {} | Erro: {}",
                operation,
                persistenceType.getDescription(),
                exception.getMessage()
        );
    }

    private int countClasses(List<AcademicClass> classes) {
        if (classes == null) {
            return 0;
        }

        return classes.size();
    }
}
