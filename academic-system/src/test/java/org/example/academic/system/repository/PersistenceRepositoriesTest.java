package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenceRepositoriesTest {

    @Test
    void testTxtPersistence() throws Exception {
        TxtAcademicDataRepository repo = new TxtAcademicDataRepository();
        AcademicClass ac = new AcademicClass("TXT1", "Txt Test");
        ac.addAssessment(new Assessment("Prova", 1.0));
        
        Path path = repo.save(List.of(ac), "target/test-data/test.txt");
        assertTrue(Files.exists(path));
        String content = Files.readString(path);
        assertTrue(content.contains("TXT1"));
        assertTrue(content.contains("Prova"));
    }

    @Test
    void testXmlPersistence() throws Exception {
        XmlAcademicDataRepository repo = new XmlAcademicDataRepository();
        AcademicClass ac = new AcademicClass("XML1", "Xml Test");
        ac.addAssessment(new Assessment("Trabalho", 1.0));
        
        Path path = repo.save(List.of(ac), "target/test-data/test.xml");
        assertTrue(Files.exists(path));
        String content = Files.readString(path);
        assertTrue(content.contains("XML1"));
        assertTrue(content.contains("Trabalho"));
    }

    @Test
    void testJsonPersistence() throws Exception {
        JsonAcademicDataRepository repo = new JsonAcademicDataRepository();
        AcademicClass ac = new AcademicClass("JSON1", "Json Test");
        ac.addAssessment(new Assessment("Projeto", 1.0));
        
        Path path = repo.save(List.of(ac), "target/test-data/test.json");
        assertTrue(Files.exists(path));
        String content = Files.readString(path);
        assertTrue(content.contains("JSON1"));
        assertTrue(content.contains("Projeto"));
    }
}
