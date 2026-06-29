package org.example.academic.system;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    // Paleta de cores
    private static final String COR_FUNDO         = "#1a1025";
    private static final String COR_PAINEL         = "#2d1f42";
    private static final String COR_ROXO_PRINCIPAL = "#7c3aed";
    private static final String COR_ROXO_HOVER     = "#6d28d9";
    private static final String COR_ROXO_CLARO     = "#ede9fe";
    private static final String COR_TEXTO_BRANCO   = "#f5f3ff";
    private static final String COR_TEXTO_CINZA    = "#a78bfa";
    private static final String COR_SUCESSO        = "#4ade80";
    private static final String COR_ERRO           = "#f87171";
    private static final String COR_BORDA          = "#4c1d95";

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
        stage.setTitle("Sistema Acadêmico — UNIFAL-MG");
        showLoginScreen(stage);
        stage.show();
    }

    // --- Helpers de estilo ---

    private Button criarBotaoPrimario(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(12, 20, 12, 20));
        btn.setFont(Font.font("System", FontWeight.BOLD, 13));
        btn.setStyle(
                "-fx-background-color: " + COR_ROXO_PRINCIPAL + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + COR_ROXO_HOVER + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: " + COR_ROXO_PRINCIPAL + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        ));
        return btn;
    }

    private Button criarBotaoSecundario(String texto) {
        Button btn = new Button(texto);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(10, 20, 10, 20));
        btn.setFont(Font.font("System", FontWeight.NORMAL, 13));
        btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + COR_TEXTO_CINZA + ";" +
                        "-fx-border-color: " + COR_BORDA + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        );
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: " + COR_PAINEL + ";" +
                        "-fx-text-fill: " + COR_ROXO_CLARO + ";" +
                        "-fx-border-color: " + COR_ROXO_PRINCIPAL + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        ));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: " + COR_TEXTO_CINZA + ";" +
                        "-fx-border-color: " + COR_BORDA + ";" +
                        "-fx-border-radius: 8;" +
                        "-fx-background-radius: 8;" +
                        "-fx-cursor: hand;"
        ));
        return btn;
    }

    private TextField criarCampo(String placeholder) {
        TextField campo = new TextField();
        campo.setPromptText(placeholder);
        campo.setPadding(new Insets(10));
        campo.setStyle(
                "-fx-background-color: " + COR_FUNDO + ";" +
                        "-fx-text-fill: " + COR_TEXTO_BRANCO + ";" +
                        "-fx-border-color: " + COR_BORDA + ";" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-prompt-text-fill: " + COR_TEXTO_CINZA + ";"
        );
        return campo;
    }

    private PasswordField criarCampoSenha(String placeholder) {
        PasswordField campo = new PasswordField();
        campo.setPromptText(placeholder);
        campo.setPadding(new Insets(10));
        campo.setStyle(
                "-fx-background-color: " + COR_FUNDO + ";" +
                        "-fx-text-fill: " + COR_TEXTO_BRANCO + ";" +
                        "-fx-border-color: " + COR_BORDA + ";" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-prompt-text-fill: " + COR_TEXTO_CINZA + ";"
        );
        return campo;
    }

    private Label criarTitulo(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 22));
        lbl.setStyle("-fx-text-fill: " + COR_TEXTO_BRANCO + ";");
        return lbl;
    }

    private Label criarSubtitulo(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("System", FontWeight.NORMAL, 13));
        lbl.setStyle("-fx-text-fill: " + COR_TEXTO_CINZA + ";");
        return lbl;
    }

    private Label criarRotulo(String texto) {
        Label lbl = new Label(texto);
        lbl.setFont(Font.font("System", FontWeight.BOLD, 12));
        lbl.setStyle("-fx-text-fill: " + COR_TEXTO_CINZA + ";");
        return lbl;
    }

    private VBox criarPainel(double largura) {
        VBox painel = new VBox(14);
        painel.setPadding(new Insets(32));
        painel.setMaxWidth(largura);
        painel.setStyle(
                "-fx-background-color: " + COR_PAINEL + ";" +
                        "-fx-background-radius: 16;" +
                        "-fx-border-color: " + COR_BORDA + ";" +
                        "-fx-border-radius: 16;"
        );
        return painel;
    }

    private StackPane criarFundo(VBox painel) {
        StackPane fundo = new StackPane(painel);
        fundo.setStyle("-fx-background-color: " + COR_FUNDO + ";");
        fundo.setPadding(new Insets(40));
        return fundo;
    }

    private TextArea criarAreaTexto() {
        TextArea area = new TextArea();
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefHeight(220);
        area.setStyle(
                "-fx-control-inner-background: " + COR_FUNDO + ";" +
                        "-fx-text-fill: " + COR_TEXTO_BRANCO + ";" +
                        "-fx-border-color: " + COR_BORDA + ";" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-font-family: monospace;" +
                        "-fx-font-size: 12;"
        );
        return area;
    }

    // --- Telas ---

    // TUS-2407: Tela de login
    public void showLoginScreen(Stage stage) {
        VBox painel = criarPainel(340);

        Label titulo = criarTitulo("Sistema Acadêmico");
        Label subtitulo = criarSubtitulo("UNIFAL-MG — Orientação a Objetos");

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        TextField txtUsuario = criarCampo("Usuário");
        PasswordField txtSenha = criarCampoSenha("Senha");

        Label lblErro = new Label();
        lblErro.setStyle("-fx-text-fill: " + COR_ERRO + "; -fx-font-size: 12;");
        lblErro.setWrapText(true);

        Button btnLogin = criarBotaoPrimario("Entrar");
        btnLogin.setOnAction(e -> {
            try {
                User user = authenticationController.authenticate(
                        txtUsuario.getText(), txtSenha.getText()
                );
                showMainScreen(stage, user);
            } catch (AuthenticationException ex) {
                lblErro.setText("Usuário ou senha inválidos.");
            }
        });

        painel.getChildren().addAll(titulo, subtitulo, sep,
                criarRotulo("USUÁRIO"), txtUsuario,
                criarRotulo("SENHA"), txtSenha,
                btnLogin, lblErro);
        painel.setAlignment(Pos.CENTER_LEFT);

        StackPane fundo = criarFundo(painel);
        stage.setScene(new Scene(fundo, 460, 420));
    }

    // TUS-2408: Tela principal baseada em papel
    public void showMainScreen(Stage stage, User user) {
        VBox painel = criarPainel(380);

        String papel = user.getRole() == Role.ADMIN ? "Administrador" : "Professor";
        Label titulo = criarTitulo("Bem-vindo!");
        Label subtitulo = criarSubtitulo(user.getUsername() + " · " + papel);

        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        painel.getChildren().addAll(titulo, subtitulo, sep);

        if (user.getRole() == Role.ADMIN) {
            Label secaoAdmin = criarRotulo("ADMINISTRAÇÃO");
            Button btnCadastrarTurma = criarBotaoPrimario("Cadastrar Turma");
            btnCadastrarTurma.setOnAction(e -> showClassRegistrationScreen(stage, user));

            Button btnConfigurarPersistencia = criarBotaoPrimario("Configurar Persistência");
            btnConfigurarPersistencia.setOnAction(e -> showPersistenceConfigScreen(stage, user));

            Button btnSalvar = criarBotaoPrimario("Salvar Dados");
            btnSalvar.setOnAction(e -> {
                String result = controller.saveAcademicData();
                showAlert("Sucesso", result);
            });

            painel.getChildren().addAll(secaoAdmin, btnCadastrarTurma, btnConfigurarPersistencia, btnSalvar);

            Separator sep2 = new Separator();
            sep2.setStyle("-fx-background-color: " + COR_BORDA + ";");
            painel.getChildren().add(sep2);
        }

        Label secaoGeral = criarRotulo("ACADÊMICO");
        Button btnCadastrarAvaliacao = criarBotaoPrimario("Cadastrar Avaliação");
        btnCadastrarAvaliacao.setOnAction(e -> showAssessmentRegistrationScreen(stage, user));

        Button btnRelatorios = criarBotaoPrimario("Relatórios");
        btnRelatorios.setOnAction(e -> showReportScreen(stage, user));

        Button btnVisualizar = criarBotaoPrimario("Visualizar Turmas e Avaliações");
        btnVisualizar.setOnAction(e -> showVisualizationScreen(stage, user));

        Button btnLogout = criarBotaoSecundario("Sair da conta");
        btnLogout.setOnAction(e -> {
            authenticationController.logout();
            showLoginScreen(stage);
        });

        painel.getChildren().addAll(secaoGeral, btnCadastrarAvaliacao, btnRelatorios, btnVisualizar, btnLogout);

        StackPane fundo = criarFundo(painel);
        stage.setScene(new Scene(fundo, 480, user.getRole() == Role.ADMIN ? 620 : 440));
    }

    // TUS-2409: Tela de cadastro de turmas
    private void showClassRegistrationScreen(Stage stage, User user) {
        VBox painel = criarPainel(380);

        Label titulo = criarTitulo("Cadastrar Turma");
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        TextField txtCodigo = criarCampo("Ex: CC001");
        TextField txtNome = criarCampo("Ex: Orientação a Objetos");

        Label lblMensagem = new Label();
        lblMensagem.setWrapText(true);
        lblMensagem.setFont(Font.font("System", 12));

        Button btnCadastrar = criarBotaoPrimario("Cadastrar");
        btnCadastrar.setOnAction(e -> {
            try {
                controller.registerClass(txtCodigo.getText(), txtNome.getText());
                lblMensagem.setStyle("-fx-text-fill: " + COR_SUCESSO + ";");
                lblMensagem.setText("✓ Turma cadastrada com sucesso!");
                txtCodigo.clear();
                txtNome.clear();
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: " + COR_ERRO + ";");
                lblMensagem.setText("✗ " + ex.getMessage());
            }
        });

        Button btnVoltar = criarBotaoSecundario("← Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        painel.getChildren().addAll(titulo, sep,
                criarRotulo("CÓDIGO DA TURMA"), txtCodigo,
                criarRotulo("NOME DA TURMA"), txtNome,
                btnCadastrar, lblMensagem, btnVoltar);

        StackPane fundo = criarFundo(painel);
        stage.setScene(new Scene(fundo, 480, 420));
    }

    // TUS-2410: Tela de cadastro de avaliacoes
    private void showAssessmentRegistrationScreen(Stage stage, User user) {
        VBox painel = criarPainel(380);

        Label titulo = criarTitulo("Cadastrar Avaliação");
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        TextField txtCodigo = criarCampo("Código da turma, ex: CC001");
        TextField txtNome = criarCampo("Ex: Prova 1");
        TextField txtPeso = criarCampo("Ex: 0.4");

        Label lblMensagem = new Label();
        lblMensagem.setWrapText(true);
        lblMensagem.setFont(Font.font("System", 12));

        Button btnCadastrar = criarBotaoPrimario("Cadastrar");
        btnCadastrar.setOnAction(e -> {
            try {
                double peso = Double.parseDouble(txtPeso.getText().replace(",", "."));
                controller.registerAssessment(txtCodigo.getText(), txtNome.getText(), peso);
                lblMensagem.setStyle("-fx-text-fill: " + COR_SUCESSO + ";");
                lblMensagem.setText("✓ Avaliação cadastrada com sucesso!");
                txtCodigo.clear();
                txtNome.clear();
                txtPeso.clear();
            } catch (NumberFormatException ex) {
                lblMensagem.setStyle("-fx-text-fill: " + COR_ERRO + ";");
                lblMensagem.setText("✗ Peso inválido. Use um número como 0.4");
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: " + COR_ERRO + ";");
                lblMensagem.setText("✗ " + ex.getMessage());
            }
        });

        Button btnVoltar = criarBotaoSecundario("← Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        painel.getChildren().addAll(titulo, sep,
                criarRotulo("CÓDIGO DA TURMA"), txtCodigo,
                criarRotulo("NOME DA AVALIAÇÃO"), txtNome,
                criarRotulo("PESO"), txtPeso,
                btnCadastrar, lblMensagem, btnVoltar);

        StackPane fundo = criarFundo(painel);
        stage.setScene(new Scene(fundo, 480, 460));
    }

    // TUS-2411: Tela de relatorios
    private void showReportScreen(Stage stage, User user) {
        VBox painel = criarPainel(680);

        Label titulo = criarTitulo("Relatórios");
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        TextArea txtRelatorio = criarAreaTexto();
        // Remove o limite de altura preferida fixa e deixa o TextArea
        // crescer para ocupar o espaco vertical que sobrar no painel.
        txtRelatorio.setPrefHeight(420);
        VBox.setVgrow(txtRelatorio, Priority.ALWAYS);

        Label rotuloRelatorios = criarRotulo("SELECIONE UM RELATÓRIO");

        Button btnResumo = criarBotaoPrimario("Resumo de Avaliações por Turma");
        btnResumo.setOnAction(e -> txtRelatorio.setText(controller.generateClassAssessmentSummaryReport()));

        Button btnPeso = criarBotaoPrimario("Relatório de Peso das Avaliações");
        btnPeso.setOnAction(e -> txtRelatorio.setText(controller.generateAssessmentWeightReport()));

        // Botoes de selecao de relatorio em um FlowPane: cada botao
        // mantem sua largura preferida (texto completo, sem cortar) e,
        // se nao houver espaco suficiente na linha, o botao seguinte
        // "quebra" para a proxima linha automaticamente.
        btnResumo.setMaxWidth(Region.USE_PREF_SIZE);
        btnPeso.setMaxWidth(Region.USE_PREF_SIZE);

        FlowPane botoesRelatorio = new FlowPane(10, 10, btnResumo, btnPeso);

        if (user.getRole() == Role.ADMIN) {
            Button btnPersistencia = criarBotaoPrimario("Configuração de Persistência");
            btnPersistencia.setOnAction(e -> txtRelatorio.setText(controller.generatePersistenceConfigurationReport()));
            btnPersistencia.setMaxWidth(Region.USE_PREF_SIZE);
            botoesRelatorio.getChildren().add(btnPersistencia);
        }

        Separator sep2 = new Separator();
        sep2.setStyle("-fx-background-color: " + COR_BORDA + ";");

        Button btnVoltar = criarBotaoSecundario("← Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        painel.getChildren().addAll(titulo, sep, rotuloRelatorios, botoesRelatorio,
                sep2, criarRotulo("RESULTADO"), txtRelatorio, btnVoltar);
        VBox.setVgrow(painel, Priority.ALWAYS);

        StackPane fundo = criarFundo(painel);
        Scene scene = new Scene(fundo, 820, 720);
        stage.setScene(scene);

        // Permite redimensionar a janela e a area de resultado acompanhar
        // o novo tamanho.
        stage.setMinWidth(680);
        stage.setMinHeight(560);
    }

    // TUS-2412: Tela de configuracao de persistencia
    private void showPersistenceConfigScreen(Stage stage, User user) {
        VBox painel = criarPainel(360);

        Label titulo = criarTitulo("Persistência");
        Label subtitulo = criarSubtitulo("Selecione o formato de salvamento dos dados");
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        ToggleGroup grupo = new ToggleGroup();

        RadioButton rbTxt = new RadioButton("TXT — Arquivo de texto simples");
        RadioButton rbXml = new RadioButton("XML — Formato estruturado");
        RadioButton rbJson = new RadioButton("JSON — Formato leve e interoperável");

        for (RadioButton rb : new RadioButton[]{rbTxt, rbXml, rbJson}) {
            rb.setToggleGroup(grupo);
            rb.setStyle("-fx-text-fill: " + COR_TEXTO_BRANCO + "; -fx-font-size: 13;");
        }
        rbTxt.setSelected(true);

        Label lblMensagem = new Label();
        lblMensagem.setWrapText(true);
        lblMensagem.setFont(Font.font("System", 12));

        Button btnConfirmar = criarBotaoPrimario("Confirmar");
        btnConfirmar.setOnAction(e -> {
            try {
                PersistenceType tipo;
                if (rbXml.isSelected()) tipo = PersistenceType.XML;
                else if (rbJson.isSelected()) tipo = PersistenceType.JSON;
                else tipo = PersistenceType.TXT;

                controller.configurePersistenceType(tipo);
                lblMensagem.setStyle("-fx-text-fill: " + COR_SUCESSO + ";");
                lblMensagem.setText("✓ Persistência configurada: " + tipo.getDescription());
            } catch (Exception ex) {
                lblMensagem.setStyle("-fx-text-fill: " + COR_ERRO + ";");
                lblMensagem.setText("✗ " + ex.getMessage());
            }
        });

        Button btnVoltar = criarBotaoSecundario("← Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        painel.getChildren().addAll(titulo, subtitulo, sep,
                criarRotulo("FORMATO"), rbTxt, rbXml, rbJson,
                btnConfirmar, lblMensagem, btnVoltar);

        StackPane fundo = criarFundo(painel);
        stage.setScene(new Scene(fundo, 460, 460));
    }

    // TUS-2413: Tela de visualizacao
    private void showVisualizationScreen(Stage stage, User user) {
        VBox painel = criarPainel(620);

        Label titulo = criarTitulo("Turmas e Avaliações");
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: " + COR_BORDA + ";");

        TextArea txtDados = criarAreaTexto();
        txtDados.setPrefHeight(420);
        txtDados.setText(controller.listClassesAndAssessments());
        VBox.setVgrow(txtDados, Priority.ALWAYS);

        Button btnVoltar = criarBotaoSecundario("← Voltar");
        btnVoltar.setOnAction(e -> showMainScreen(stage, user));

        painel.getChildren().addAll(titulo, sep, criarRotulo("DADOS CADASTRADOS"), txtDados, btnVoltar);
        VBox.setVgrow(painel, Priority.ALWAYS);

        StackPane fundo = criarFundo(painel);
        Scene scene = new Scene(fundo, 760, 660);
        stage.setScene(scene);

        stage.setMinWidth(620);
        stage.setMinHeight(500);
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