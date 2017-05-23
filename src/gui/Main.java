package gui;

import cs_9roject.Event;
import cs_9roject.Project;
import cs_9roject.Timeline;
import cs_9roject.TimelinesDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

// @TODO: Make timeline display after it's created (no need to select it afterwards, make optional)

public class Main extends Application {
	
	
	private Stage primaryStage;
	public BorderPane mainLayout;
	public static Project project = new Project();
	public static TimelinesDAO dao = new TimelinesDAO();
	public static int userID=0;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Timelines");	
		this.primaryStage.setResizable(true);
	showMainView();
	loginWindow();
	project.userID=userID;
	}
	
	private void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("StartingMode.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
		
	public static void showEventHandler() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("eventHandler.fxml"));
		Pane showEventHandler = loader.load();
		Stage stage2 = new Stage();
        stage2.setScene(new Scene(showEventHandler));  
        stage2.setTitle("Add new event");
        stage2.showAndWait();		
	}

	/**
	 * Method for displaying the login window.
	 */
	public static void loginWindow() throws IOException{
		/*FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("LoginHandler.fxml"));
		FlowPane login = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(login));
		stage.setTitle("Log In Window");
		stage.showAndWait();
		userID=0;*/
		
		// Create the custom dialog.
		Dialog <Integer>  dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Enter your user name and password");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		    	int userid=-1;
		    	
		    	if (username.getText().toLowerCase().equals("admin" ) && password.getText().toLowerCase().equals("admin" )){
		    		userid=1000000;
		    	}	
			    return userid;
		    }
		    return 0;
		});
		Optional<Integer> result = dialog.showAndWait();
		userID = result.get();
		if (userID==-1){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot log in!");
			alert.setContentText("Wrong user name or password");
			alert.showAndWait();
			loginWindow();
		}
			
	}

	public static void main(String[] args) {
		Event.setCount(dao.getHighestEventID() + 1);
		Timeline.setCount(dao.getHighestTimelineID() + 1);
		project.ProjectID =(dao.getHighestProjectID() + 1);
		project.projectName="Project " + project.ProjectID;
		Project.setCount(project.ProjectID + 1);
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
		launch(args);
	}
}