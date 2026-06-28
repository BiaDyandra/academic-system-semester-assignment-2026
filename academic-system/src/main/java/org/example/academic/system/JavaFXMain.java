package org.example.academic.system;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.academic.system.controller.AcademicSystemController;
import org.example.academic.system.controller.AuthenticationController;
import org.example.academic.system.exception.AuthenticationException;
import org.example.academic.system.repository.AcademicClassRepository;
import org.example.academic.system.repository.JsonAcademicDataRepository;
import org.example.academic.system.repository.PersistenceType;
import org.example.academic.system.repository.TxtAcademicDataRepository;
import org.example.academic.system.repository.XmlAcademicDataRepository;
import org.example.academic.system.security.Role;
import org.example.academic.system.security.User;
import org.example.academic.system.service.AssessmentService;
import org.example.academic.system.service.AuthenticationService;
import org.example.academic.system.service.AuthorizationService;
import org.example.academic.system.service.ClassService;
import org.example.academic.system.service.PersistenceService;
import org.example.academic.system.service.ReportService;

public class JavaFXMain extends Application {

    private AuthenticationController authenticationController;
    private AcademicSystemController controller;

    @Override
    public void init() {
        AcademicClassRepository academicClassRepository = new AcademicClassRepository();
        ClassService classService = new ClassService(academicClassRepository);
        AssessmentService assessmentService = new AssessmentService(academicClassRepository);
        TxtAcademicDataRepository txtRepo = new TxtAcademicDataRepository();
        XmlAcademicDataRepository xmlRepo = new XmlAcademicDataRepository();
        JsonAcademicDataRepository jsonRepo = new JsonAcademicDataRepository();
        PersistenceService persistenceService = new PersistenceService(txtRepo, xmlRepo, jsonRepo);
        ReportService reportService = new ReportService();
        AuthenticationService authenticationService = new AuthenticationService();
        AuthorizationService authorizationService = new AuthorizationService();

        authenticationController = new AuthenticationController(authenticationService);

        controller = new AcademicSystemController(
                classService, assessmentService, persistenceService,
                reportService, authenticationService, authorizationService
        );
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Sistema Academico");
        showLoginScreen(stage);
        stage.show();
    }

    // TUS-2407: Tela de login
    public void showLoginScreen(Stage stage) {
        Label lblUsuario = new Label("Usuario:");
        TextField txtUsuario = new TextField();

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();

        Label lblErro = new Label();
        lblErro.setStyle("-fx-text-fill: red;");

        Button btnLogin = new Button("Entrar");
        btnLogin.setOnAction(e -> {
            try {
                User user = authenticationController.authenticate(
                        txtUsuario.getText(), txtSenha.getText()
                );
                showMainScreen(stage, user);
            } catch (AuthenticationException ex) {
                lblErro.setText("Usuario ou senha invalidos.");
            }
        });

        VBox layout = new VBox(10, lblUsuario, txtUsuario, lblSenha, txtSenha, btnLogin, lblErro);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 300, 230));
    }

    // TUS-2408: Tela principal baseada em papel
    public void showMainScreen(Stage stage, User user) {
        Label lblBemVindo = new Label("Bem-vindo, " + user.getUsername() + " [" + user.getRole() + "]");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().add(lblBemVindo);

        if (user.getRole() == Role.ADMIN) {
            Button btnCadastrarTurma = new Button("Cadastrar Turma");
            btnCadastrarTurma.setOnAction(e -> showClassRegistrationScreen(stage, user));

            Button btnConfigurarPersistencia = new Button("Configurar Persistencia");
            btnConfigurarPersistencia.setOnAction(e -> showPersistenceConfigScreen(stage, user));

            Button btnSalvar = new Button("Salvar Dados");
            btnSalvar.setOnAction(e -> {
                String result = controller.saveAcademicData();
                showAlert("Sucesso", result);
            });

            layout.getChildren().addAll(btnCadastrarTurma, btnConfigurarPersistencia, btnSalvar);
        }

        Button btnCadastrarAvaliacao = new Button("Cadastrar Avaliacao");
        btnCadastrarAvaliacao.setOnAction(e -> showAssessmentRegistrationScreen(stage, user));

        Button btnRelatorios = new Button("Relatorios");
        btnRelatorios.setOnAction(e -> showReportScreen(stage, user));

        Button btnVisualizar = new Button("Visualizar Turmas e Avaliacoes");
        btnVisualizar.setOnAction(e -> showVisualizationScreen(stage, user));

        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> {
            authenticationController.logout();
            showLoginScreen(stage);
        });

        layout.getChildren().addAll(btnCadastrarAvaliacao, btnRelatorios, btnVisualizar, btnLogout);
        stage.setScene(new Scene(layout, 400, 380));
    }

    // TUS-2409: Tela de cadastro de turmas
    private void showClassRegistrationScreen(Stage stage, User user) {
        Label lblCodigo = new Label("Codigo:");
        TextField txtCodigo = new TextField();

        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();

        Label lblMensagem = new Label();

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setOnAction(e -> {
            try {
                controller.registerClass(txtCodigo.getText(), txtNome.getText());
                lblMensagem.setStyle("-fx-text-fill: green;");
                lblMensagem.setText("Turma cadastrada com sucesso!");
                txtCodigo.clear();
                txtNome.clear();
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        VBox layout = new VBox(10, lblCodigo, txtCodigo, lblNome, txtNome, btnCadastrar, lblMensagem, btnVoltar);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 280));
    }

    // TUS-2410: Tela de cadastro de avaliacoes
    private void showAssessmentRegistrationScreen(Stage stage, User user) {
        Label lblCodigo = new Label("Codigo da Turma:");
        TextField txtCodigo = new TextField();

        Label lblNome = new Label("Nome da Avaliacao:");
        TextField txtNome = new TextField();

        Label lblPeso = new Label("Peso (ex: 0.4):");
        TextField txtPeso = new TextField();

        Label lblMensagem = new Label();

        Button btnCadastrar = new Button("Cadastrar");
        btnCadastrar.setOnAction(e -> {
            try {
                double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                controller.registerAssessment(txtCodigo.getText(), txtNome.getText(), peso);
                lblMensagem.setStyle("-fx-text-fill: green;");
                lblMensagem.setText("Avaliacao cadastrada com sucesso!");
                txtCodigo.clear();
                txtNome.clear();
                txtPeso.clear();
            } catch (NumberFormatException ex) {
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("Peso invalido. Use um numero como 0.4");
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        VBox layout = new VBox(10, lblCodigo, txtCodigo, lblNome, txtNome,
                lblPeso, txtPeso, btnCadastrar, lblMensagem, btnVoltar);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 400, 320));
    }

    // TUS-2411: Tela de relatorios
    private void showReportScreen(Stage stage, User user) {
        TextArea txtRelatorio = new TextArea();
        txtRelatorio.setEditable(false);
        txtRelatorio.setPrefHeight(250);

        Button btnResumo = new Button("Resumo de Avaliacoes por Turma");
        btnResumo.setOnAction(e -> txtRelatorio.setText(controller.generateClassAssessmentSummaryReport()));

        Button btnPeso = new Button("Relatorio de Peso das Avaliacoes");
        btnPeso.setOnAction(e -> txtRelatorio.setText(controller.generateAssessmentWeightReport()));

        VBox layout = new VBox(10, btnResumo, btnPeso);
        layout.setPadding(new Insets(20));

        if (user.getRole() == Role.ADMIN) {
            Button btnPersistencia = new Button("Relatorio de Configuracao de Persistencia");
            btnPersistencia.setOnAction(e -> txtRelatorio.setText(controller.generatePersistenceConfigurationReport()));
            layout.getChildren().add(btnPersistencia);
        }

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        layout.getChildren().addAll(txtRelatorio, btnVoltar);
        stage.setScene(new Scene(layout, 500, 430));
    }

    // TUS-2412: Tela de configuracao de persistencia
    private void showPersistenceConfigScreen(Stage stage, User user) {
        Label lblTitulo = new Label("Selecione o tipo de persistencia:");

        ToggleGroup grupo = new ToggleGroup();
        RadioButton rbTxt = new RadioButton("TXT");
        RadioButton rbXml = new RadioButton("XML");
        RadioButton rbJson = new RadioButton("JSON");
        rbTxt.setToggleGroup(grupo);
        rbXml.setToggleGroup(grupo);
        rbJson.setToggleGroup(grupo);
        rbTxt.setSelected(true);

        Label lblMensagem = new Label();

        Button btnConfirmar = new Button("Confirmar");
        btnConfirmar.setOnAction(e -> {
            try {
                PersistenceType tipo;
                if (rbXml.isSelected()) {
                    tipo = PersistenceType.XML;
                } else if (rbJson.isSelected()) {
                    tipo = PersistenceType.JSON;
                } else {
                    tipo = PersistenceType.TXT;
                }
                controller.configurePersistenceType(tipo);
                lblMensagem.setStyle("-fx-text-fill: green;");
                lblMensagem.setText("Persistencia configurada: " + tipo.getDescription());
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: red;");
                lblMensagem.setText("Erro: " + ex.getMessage());
            }
        });

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        VBox layout = new VBox(10, lblTitulo, rbTxt, rbXml, rbJson, btnConfirmar, lblMensagem, btnVoltar);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 350, 280));
    }

    // TUS-2413: Tela de visualizacao
    private void showVisualizationScreen(Stage stage, User user) {
        TextArea txtDados = new TextArea();
        txtDados.setEditable(false);
        txtDados.setPrefHeight(300);
        txtDados.setText(controller.listClassesAndAssessments());

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        VBox layout = new VBox(10, txtDados, btnVoltar);
        layout.setPadding(new Insets(20));
        stage.setScene(new Scene(layout, 500, 380));
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
