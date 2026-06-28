package org.example.academic.system.repository;

import org.example.academic.system.model.AcademicClass;
import org.example.academic.system.model.Assessment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class XmlAcademicDataRepository {

    private static final String DEFAULT_FILE_PATH = "data/academic-data.xml";

    public Path save(List<AcademicClass> classes) {
        return save(classes, DEFAULT_FILE_PATH);
    }

    public Path save(List<AcademicClass> classes, String filePath) {
        Path path = Paths.get(filePath);

        try {
            createParentDirectories(path);
            Document document = buildXmlDocument(classes);
            writeDocumentToFile(document, path);
            return path;
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new IllegalStateException("Nao foi possivel salvar os dados em XML.", e);
        }
    }

    private void createParentDirectories(Path path) throws IOException {
        Path parent = path.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    private Document buildXmlDocument(List<AcademicClass> classes) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("academicData");
        document.appendChild(root);

        Element classesElement = document.createElement("classes");
        root.appendChild(classesElement);

        for (AcademicClass academicClass : classes) {
            Element classElement = document.createElement("class");
            classElement.setAttribute("code", academicClass.getCode());
            classesElement.appendChild(classElement);

            Element classNameElement = document.createElement("name");
            classNameElement.setTextContent(academicClass.getName());
            classElement.appendChild(classNameElement);

            Element assessmentsElement = document.createElement("assessments");
            classElement.appendChild(assessmentsElement);

            for (Assessment assessment : academicClass.getAssessments()) {
                Element assessmentElement = document.createElement("assessment");
                assessmentsElement.appendChild(assessmentElement);

                Element assessmentNameElement = document.createElement("name");
                assessmentNameElement.setTextContent(assessment.getName());
                assessmentElement.appendChild(assessmentNameElement);

                Element assessmentWeightElement = document.createElement("weight");
                assessmentWeightElement.setTextContent(String.valueOf(assessment.getWeight()));
                assessmentElement.appendChild(assessmentWeightElement);
            }
        }

        return document;
    }

    private void writeDocumentToFile(Document document, Path path) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(document), new StreamResult(path.toFile()));
    }
}
