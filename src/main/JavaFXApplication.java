package main;

import java.io.File;

import org.w3c.dom.Document;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.scene.effect.Bloom;
/**
 * Exemplo de aplicação simples utilizando JavaFX.
 * 
 * 
 * @author Vladwoguer Bezerra
 * 
 * I'd like to thank jewelsea for this gist about splash screen on JavaFX: 
 * https://gist.github.com/jewelsea/1588531
 * 
 */
public class JavaFXApplication extends Application {
	private static final int FULL_LOADED_PROGRESS = 1;
	private static final int BUTTON_PREF_WIDTH = 200;
	private static final int BUTTON_PREF_HEIGHT = 40;
	private static final double FINAL_SCREEN_EFFECT_OPACITY = 0.0;
	private static final double INITIAL_SCREEN_EFFECT_OPACITY = 1.0;
	private static final double EFFECT_DURATION = 1.2;
	private static final String DATA_SUCCESSFULLY_LOADED = "Dados carregados com sucesso!";
	private static final int TOTAL_PROGRESS = 100;
	private static final int FORM_LOGIN_HEIGHT = 600;
	private static final int FORM_LOGIN_WIDTH = 800;
	private static final int WEB_VIEW_HEIGHT = 600;
	private static final int WEB_VIEW_WIDTH = 800;
	private static final String WELCOME_MODAL_MESSAGE = "Bem vindo: ";
	private static final String WELCOME_MODAL_TITLE = "Bem vindo";
	private static final String LOGIN = "Login";
	private static final String PASSWORD_FIELD_LABEL = "Senha: ";
	private static final String LOGIN_FIELD_LABEL = "Login: ";
	private static final String EMPTY_FIELD = "Campo Vazio: ";
	private static final String FORM_VALIDATION_MODAL_ERROR = "Erro no formulário: ";
	private static final String LOGIN_BUTTON = "Efetuar login";
	private static final int FORM_FONT_SIZE = 24;
	private static final FontWeight FORM_FONT_WEIGHT = FontWeight.BOLD;
	private static final String FORM_FONT_NAME = "Arial";
	private static final String FORM_LABEL_TITLE = "Registration Form";
	private static final int COLUMN_WIDTH = 100;
	private static final double COLUMN_MAX_WIDTH = Double.MAX_VALUE;
	private static final int FORM_PADDING = 40;
	private static final String WEB_VIEW_URL_TO_SHOW = "http://github.com/vladwoguer";
	private static final String WEB_VIEW_SCREEN_TITLE = "Aplicação JavaFX";
	private static final String SPLASH_SCREEN_LAYOUT = 
			"-fx-padding: 5;" + 
	        " -fx-background-color: cornsilk;" + 
			" -fx-border-width:5;" + 
	        " -fx-border-color:" + 
					" linear-gradient(" + 
									  "to bottom," + 
									  " chocolate," + 
									  " derive(chocolate, 50%));";
	private static final String LOADING_INITIAL_MESSAGE = "Carregando dados...";
	private static final String RESOURCES_IMAGES_SPLASH_SCREEN_JPG = 
			"resources/images/splash-screen.jpg";
	private Pane splashScreenLayout;
	private ProgressBar loadingStatus;
	private Label loadingLabel;
	private WebView webView;
	private Stage applicationStage;
	private static final int SPLASH_WIDTH = 500;
	private static final int SPLASH_HEIGHT = 200;
	private static final String PASSWORD = "Senha";

	/**
	 * Main da aplicação, inicia a aplicação gráfica.
	 */
	public static void main(String[] args) throws Exception {
		JavaFXApplication.launch(args);
	}

	/**
	 * Realiza os passos inicias da aplicação, reenderizando a tela de 
	 * apresentação.
	 */
	@Override
	public void init() {
		File image = new File(RESOURCES_IMAGES_SPLASH_SCREEN_JPG);
		ImageView splash = new ImageView(new Image(image.toURI().toString()));
		loadingStatus = new ProgressBar();
		loadingStatus.setPrefWidth(SPLASH_WIDTH - 20);
		loadingLabel = new Label(LOADING_INITIAL_MESSAGE);
		splashScreenLayout = new VBox();
		splashScreenLayout.getChildren().addAll(splash, loadingStatus, 
				loadingLabel);
		loadingLabel.setAlignment(Pos.CENTER);
		splashScreenLayout.setStyle(
				SPLASH_SCREEN_LAYOUT);
		splashScreenLayout.setEffect(new DropShadow());
	}

	@Override
	public void start(final Stage splashScreenStage) throws Exception {
		//Mostra a tela de apresentação
		showSplashScreen(splashScreenStage);
		//Mostra a tela principal da aplicação
		showMainStage();
		
		//Adicionamos aqui um listener para associar o caregamento da página
		//ao seu progresso
		webView.getEngine().documentProperty().addListener(
				(ObservableValue<? extends Document> observableValue, Document document, Document document1) -> {
					
					//Verifica se a splash screen está visível
					if (splashScreenStage.isShowing()) {
						//Prepara o progresso para ser iniciado
						loadingStatus.progressProperty().unbind();
						//Configura o progresso em 100%
						loadingStatus.setProgress(FULL_LOADED_PROGRESS);
						//Avisa do careegamento dos dados
						loadingLabel.setText(DATA_SUCCESSFULLY_LOADED);
						//Deixa a aplicação em primeiro plano
						applicationStage.setIconified(false);
						splashScreenStage.toFront();
						//Adiciona um efeito para a transição
						FadeTransition fadeSplash = new FadeTransition(
								Duration.seconds(EFFECT_DURATION), splashScreenLayout);
						//Configura a opacidade inicial da tela no efeito
						fadeSplash.setFromValue(INITIAL_SCREEN_EFFECT_OPACITY);
						//Configura a opacidade final da tela no efeito
						fadeSplash.setToValue(FINAL_SCREEN_EFFECT_OPACITY);
						//Adiciona o evento ao término do efeito
						fadeSplash.setOnFinished((ActionEvent actionEvent) -> {
							//Esconde a tela de splash screen
							splashScreenStage.hide();
							//Coloca a tela de login em primeiro plano
							applicationStage.toFront();
						});
						//Inicia o efeito de transição
						fadeSplash.play();
					}
				});
	}

	/**
	 * Mostra a tela principal da aplicação.
	 */
	private void showMainStage() {
		applicationStage = new Stage(StageStyle.DECORATED);
		applicationStage.setTitle(WEB_VIEW_SCREEN_TITLE);
		applicationStage.setIconified(true);

		//Cria a webview
		webView = new WebView();
		webView.getEngine().load(WEB_VIEW_URL_TO_SHOW);
		/*
		 * Aqui fazemos a ligação do carregamento da webview o nosso status
		 * Isso serve para que possamos saber quando a webview carregou e 
		 * trocar o status
		 */
		loadingStatus.progressProperty().bind(
				webView.getEngine().getLoadWorker()
					.workDoneProperty().divide(TOTAL_PROGRESS));

		// Aqui criamos o formulário
		GridPane gridPane = createRegistrationFormPane();
		// Os controles do formulário
		addUIControlsForPane(gridPane);
		// Aqui vem nosso form de login
		Scene sceneLogin = new Scene(gridPane, FORM_LOGIN_WIDTH, 
				FORM_LOGIN_HEIGHT);
		// Aqui preparamos o formulário de login para ser exibido
		applicationStage.setScene(sceneLogin);
		// Formulário de login é mostrado
		applicationStage.show();
	}

	/**
	 * Esse metódo mostra a tela de apresentação(splash screen)
	 * @param stage 
	 * 					O stage que vamos usar para mostrar o splash
	 */
	private void showSplashScreen(Stage stage) {
		//Aqui criamos uma novo scene com o layout que definimos
		Scene splashScene = new Scene(splashScreenLayout);
		//Aqui definimos que queremos partir de um fundo branco sem 
		//estilização
		stage.initStyle(StageStyle.UNDECORATED);
		//Definimos uma forma retangular
		final Rectangle2D bounds = Screen.getPrimary().getBounds();
		//Preparamos o scene para ser mostrado
		stage.setScene(splashScene);
		//Defininimos a posição horizontal inicial
		stage.setX(bounds.getMinX() + bounds.getWidth() / 2 - 
				SPLASH_WIDTH / 2);
		//Defininimos a posição vertical inicial
		stage.setY(bounds.getMinY() + bounds.getHeight() / 2 - 
				SPLASH_HEIGHT / 2);
		//Tornamos a tela de apresentação visível
		stage.show();
	}

	/**
	 * Cria o formulário de login.
	 * 
	 * @return
	 * 		Um GridPane contendo nosso formulário de login
	 */
	private GridPane createRegistrationFormPane() {
		//Criamos um GridPane para nossos UIControls
		GridPane loginGridPane = new GridPane();

		//O GridPane deve ficar no meio da tela, para termos um formulário
		//centralizado
		loginGridPane.setAlignment(Pos.CENTER);

		//Adicionamos 40 de padding em todos os lados
		//Somente por questão estética
		loginGridPane.setPadding(new Insets(FORM_PADDING, FORM_PADDING, 
				FORM_PADDING, FORM_PADDING));

		// Definimos o espaço horizontal que queremos entre as Columns
		loginGridPane.setHgap(10);

		// Definimos o espaço vertical que queremos entre as Columns
		loginGridPane.setVgap(10);

		// Primeira Column
		ColumnConstraints columnOneConstraints = 
				new ColumnConstraints(COLUMN_WIDTH, COLUMN_WIDTH, 
						COLUMN_MAX_WIDTH);
		// Queremos que essa Column alinhe os elementos na direita
		columnOneConstraints.setHalignment(HPos.RIGHT);

		// Segunda Column
		ColumnConstraints columnTwoConstrains = new ColumnConstraints(
				COLUMN_WIDTH, COLUMN_WIDTH, COLUMN_MAX_WIDTH);
		//Queremos permitir que essa Column cresça horizontalmente se 
		//necessário
		columnTwoConstrains.setHgrow(Priority.ALWAYS);
		
		//Aqui inserimos as Columns
		loginGridPane.getColumnConstraints().addAll(columnOneConstraints, 
				columnTwoConstrains);

		return loginGridPane;
	}

	/**
	 * Nesse metódo adicionamos os UIControls de nosso formulário ou seja
	 * adicionamos os controles do formulário.
	 * 
	 * @param formPane
	 * 			Nosso GridPane de login
	 */
	private void addUIControlsForPane(GridPane formPane) {
		//Aqui adicionamos o cabeçalho de formulário
		Label headerLabel = new Label(FORM_LABEL_TITLE);
		//Queremos aqui usar a fonte Arial
		headerLabel.setFont(Font.font(FORM_FONT_NAME, FORM_FONT_WEIGHT, 
				FORM_FONT_SIZE));
		//Adicionamos aqui nosso título no cabeçalho
		formPane.add(headerLabel, 0, 0, 2, 1);
		// Queremos o label centralizado
		GridPane.setHalignment(headerLabel, HPos.CENTER);
		//Adicionamos margens com fins de estilização
		GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

		//Adicionamos um label pro login
		Label nameLabel = new Label(LOGIN_FIELD_LABEL);
		formPane.add(nameLabel, 0, 1);

		//Adicionamos um campo de texto para o login
		TextField nameField = new TextField();
		nameField.setPrefHeight(40);
		formPane.add(nameField, 1, 1);

		//Adicionamos um label para a senha
		Label passwordLabel = new Label(PASSWORD_FIELD_LABEL);
		formPane.add(passwordLabel, 0, 3);

		//Adicionamos um campo de password para a senha
		PasswordField passwordField = new PasswordField();
		passwordField.setPrefHeight(40);
		formPane.add(passwordField, 1, 3);

		//Botão efetuar login
		Button submitButton = new Button(LOGIN_BUTTON);
		submitButton.setPrefHeight(BUTTON_PREF_HEIGHT);
		submitButton.setDefaultButton(true);
		submitButton.setPrefWidth(BUTTON_PREF_WIDTH);
		formPane.add(submitButton, 0, 4, 2, 1);
		//Queremos o botão centralizado
		GridPane.setHalignment(submitButton, HPos.CENTER);
		GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

		//Adicionamos aqui um tratamento para o evento clicar no botão
		submitButton.setOnAction((ActionEvent event) -> {
				
				//A janela do login
				Window window = formPane.getScene().getWindow();
				
				boolean isEmptyName = checkEmpty(nameField);
				boolean isEmptyPassword = checkEmpty(passwordField);
				
				//Validação dos campos
				if (isEmptyName) {
					showValidationError(window, FORM_VALIDATION_MODAL_ERROR, 
							EMPTY_FIELD + LOGIN);
				} else {
					if (isEmptyPassword) {
							showValidationError(window,
									FORM_VALIDATION_MODAL_ERROR, 
									EMPTY_FIELD + PASSWORD);
						}
				}
				
				//Navega para webview
				if(!isEmptyName && !isEmptyPassword) {
					String userName = nameField.getText();
					showWelcomeScreen(window, userName);
				}

			}
		);
	}

	/**
	 * Exibe uma mensagem de sucesso e mostra a tela de boas vindas.
	 * @param window
	 * 			janela de onde deve partir a mensagem de boas vindas.
	 * @param userName
	 */
	private void showWelcomeScreen(Window window, String userName) {
		showAlert(Alert.AlertType.CONFIRMATION, window, WELCOME_MODAL_TITLE,
				WELCOME_MODAL_MESSAGE + userName);

		//Preparando a webview para demonstração
		Scene webViewScene = new Scene(webView, WEB_VIEW_WIDTH,
				WEB_VIEW_HEIGHT);
		webView.prefWidthProperty().bind(webViewScene.widthProperty());
		webView.prefHeightProperty().bind(webViewScene.heightProperty());
		//Mostra a webview
		applicationStage.setScene(webViewScene);
	}

	/**
	 * Verifica se o campo de texto está vazio.
	 * @param textField
	 * 			Campo a ser verificado
	 * @return
	 * 		true se o campo for vazio
	 */
	private boolean checkEmpty(TextField textField) {
		return textField.getText().isEmpty();
	}
	
	/**
	 * Mostra um modal de erro.
	 * @param window
	 * 			A janela de onde o modal deve partir
	 * @param errorTitle
	 * 			Título do modal de erro
	 * @param errorMessage
	 * 			Mensagem de erro a ser mostrada no modal
	 */
	private void showValidationError(Window window, String errorTitle, 
			String errorMessage) {
		showAlert(Alert.AlertType.ERROR, window, errorTitle,
				errorMessage);
	}

	/**
	 * Mostra modal de alerta
	 * @param alertType
	 * 			Tipo do alerta
	 * @param window
	 * 			janela que parte o alerta
	 * @param title
	 * 			Título do alerta
	 * @param message
	 * 			Mensagem do alerta
	 */
	private void showAlert(Alert.AlertType alertType, Window window, 
			String title, String message) {
		//Cria um alerta
		Alert alert = new Alert(alertType);
		//Define o titulo do alerta
		alert.setTitle(title);
		//Define o cabeçalho do alerta para: sem cabeçalho
		alert.setHeaderText(null);
		//Define a mensagem do alerta
		alert.setContentText(message);
		//Dispara o alerta
		alert.initOwner(window);
		//Torna o alerta visível
		alert.show();
	}

}