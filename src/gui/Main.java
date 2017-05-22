package gui;

import cs_9roject.Event;
import cs_9roject.Project;
import cs_9roject.Timeline;
import cs_9roject.TimelinesDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

// @TODO: Make timeline display after it's created (no need to select it afterwards, make optional)

public class Main extends Application {
	
	
	private Stage primaryStage;
	public BorderPane mainLayout;
	public static Project project = new Project();
	public static TimelinesDAO dao = new TimelinesDAO();
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Timelines");	
		this.primaryStage.setResizable(true);
	showMainView();
	loginWindow();
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
	public void loginWindow() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("LoginHandler.fxml"));
		FlowPane login = loader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(login));
		stage.setTitle("Log In Window");
		stage.showAndWait();
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