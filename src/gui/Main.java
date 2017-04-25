package gui;

import cs_9roject.Project;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	
	private Stage primaryStage;
	public BorderPane mainLayout;
	public static Project project = new Project();
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Show timeline");	
		this.primaryStage.setResizable(true);
	showMainView();
	}
	
	private void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("StartingMode.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		showEventHandler();
		
	}
		
	public static void showEventHandler() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("eventHandler.fxml"));
		Pane showEventHandler = loader.load();
		Stage stage2 = new Stage();
        stage2.setScene(new Scene(showEventHandler));  
        stage2.setTitle("EventHandler");
        stage2.show();		
	}

	public static void main(String[] args) {
		launch(args);
	}
}