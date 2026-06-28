package org.example.academic.system.service;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.repository.JsonAcademicDataRepository;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.repository.TxtAcademicDataRepository;
import org.example.academic.system.repository.XmlAcademicDataRepository;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersistenceServiceTest {

    @Test
    void shouldUseTxtAsDefaultPersistenceType() {
        TxtAcademicDataRepository txtRepository = mock(TxtAcademicDataRepository.class);
        XmlAcademicDataRepository xmlRepository = mock(XmlAcademicDataRepository.class);
        JsonAcademicDataRepository jsonRepository = mock(JsonAcademicDataRepository.class);

        PersistenceService persistenceService = new PersistenceService(
                txtRepository,
                xmlRepository,
                jsonRepository
        );

        assertEquals(PersistenceType.TXT, persistenceService.getCurrentPersistenceType());
    }

    @Test
    void shouldConfigurePersistenceTypeToXml() {
        TxtAcademicDataRepository txtRepository = mock(TxtAcademicDataRepository.class);
        XmlAcademicDataRepository xmlRepository = mock(XmlAcademicDataRepository.class);
        JsonAcademicDataRepository jsonRepository = mock(JsonAcademicDataRepository.class);

        PersistenceService persistenceService = new PersistenceService(
                txtRepository,
                xmlRepository,
                jsonRepository
        );

        persistenceService.configurePersistenceType(PersistenceType.XML);

        assertEquals(PersistenceType.XML, persistenceService.getCurrentPersistenceType());
    }

    @Test
    void shouldSaveUsingTxtRepositoryWhenCurrentTypeIsTxt() {
        TxtAcademicDataRepository txtRepository = mock(TxtAcademicDataRepository.class);
        XmlAcademicDataRepository xmlRepository = mock(XmlAcademicDataRepository.class);
        JsonAcademicDataRepository jsonRepository = mock(JsonAcademicDataRepository.class);

        PersistenceService persistenceService = new PersistenceService(
                txtRepository,
                xmlRepository,
                jsonRepository
        );

        List<AcademicClass> classes = createClasses();
        Path expectedPath = Path.of("academic-data.txt");

        when(txtRepository.save(classes)).thenReturn(expectedPath);

        Path savedPath = persistenceService.save(classes);

        assertEquals(expectedPath, savedPath);

        verify(txtRepository).save(classes);
        verify(xmlRepository, never()).save(classes);
        verify(jsonRepository, never()).save(classes);
    }

    @Test
    void shouldSaveUsingXmlRepositoryWhenCurrentTypeIsXml() {
        TxtAcademicDataRepository txtRepository = mock(TxtAcademicDataRepository.class);
        XmlAcademicDataRepository xmlRepository = mock(XmlAcademicDataRepository.class);
        JsonAcademicDataRepository jsonRepository = mock(JsonAcademicDataRepository.class);

        PersistenceService persistenceService = new PersistenceService(
                txtRepository,
                xmlRepository,
                jsonRepository
        );

        persistenceService.configurePersistenceType(PersistenceType.XML);

        List<AcademicClass> classes = createClasses();
        Path expectedPath = Path.of("academic-data.xml");

        when(xmlRepository.save(classes)).thenReturn(expectedPath);

        Path savedPath = persistenceService.save(classes);

        assertEquals(expectedPath, savedPath);

        verify(xmlRepository).save(classes);
        verify(txtRepository, never()).save(classes);
        verify(jsonRepository, never()).save(classes);
    }

    @Test
    void shouldSaveUsingJsonRepositoryWhenCurrentTypeIsJson() {
        TxtAcademicDataRepository txtRepository = mock(TxtAcademicDataRepository.class);
        XmlAcademicDataRepository xmlRepository = mock(XmlAcademicDataRepository.class);
        JsonAcademicDataRepository jsonRepository = mock(JsonAcademicDataRepository.class);

        PersistenceService persistenceService = new PersistenceService(
                txtRepository,
                xmlRepository,
                jsonRepository
        );

        persistenceService.configurePersistenceType(PersistenceType.JSON);

        List<AcademicClass> classes = createClasses();
        Path expectedPath = Path.of("academic-data.json");

        when(jsonRepository.save(classes)).thenReturn(expectedPath);

        Path savedPath = persistenceService.save(classes);

        assertEquals(expectedPath, savedPath);

        verify(jsonRepository).save(classes);
        verify(txtRepository, never()).save(classes);
        verify(xmlRepository, never()).save(classes);
    }

    private List<AcademicClass> createClasses() {
        return List.of(
                new AcademicClass("BCC301", "Orientação a Objetos")
        );
    }
}
