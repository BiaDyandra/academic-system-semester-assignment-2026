package org.example.academic.system;

import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.repository.AcademicClassRepository;
import org.example.academic.system.repository.JsonAcademicDataRepository;
import org.example.academic.system.repository.TxtAcademicDataRepository;
import org.example.academic.system.repository.XmlAcademicDataRepository;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.AuthenticationService;
import org.example.academic.system.service.AuthorizationService;
import org.example.academic.system.service.ClassService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;
import org.example.academic.system.view.CommandLineMenu;

public class AcademicSystemApplication {

    public void start() {
        AcademicClassRepository academicClassRepository = new AcademicClassRepository();

        ClassService classService = new ClassService(academicClassRepository);
        AssessmentService assessmentService = new AssessmentService(academicClassRepository);

        TxtAcademicDataRepository txtAcademicDataRepository = new TxtAcademicDataRepository();
        XmlAcademicDataRepository xmlAcademicDataRepository = new XmlAcademicDataRepository();
        JsonAcademicDataRepository jsonAcademicDataRepository = new JsonAcademicDataRepository();

        PersistenceService persistenceService = new PersistenceService(
                txtAcademicDataRepository,
                xmlAcademicDataRepository,
                jsonAcademicDataRepository
        );

        ReportService reportService = new ReportService();
        AuthenticationService authenticationService = new AuthenticationService();
        AuthorizationService authorizationService = new AuthorizationService();

        AcademicSystemController controller = new AcademicSystemController(
                classService,
                assessmentService,
                persistenceService,
                reportService,
                authenticationService,
                authorizationService
        );

        CommandLineMenu menu = new CommandLineMenu(controller);

        System.out.println("=================================");
        System.out.println("   Sistema Academico iniciado");
        System.out.println("=================================");

        menu.show();
    }
}
