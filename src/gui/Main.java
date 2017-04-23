package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	public FlowPane mainLayout;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Show timeline");	
		this.primaryStage.setResizable(false);
	showMainView();

	
	}
	
	private void showMainView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("StartingMode.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void showCreateMode() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("CreateMode.fxml"));
		FlowPane createMode = loader.load();
		Stage stage = new Stage();
        stage.setScene(new Scene(createMode));  
        stage.setTitle("CreateMode");
        stage.show();	
        
	}
	
	public static void showShowTimeline() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("showTimelines.fxml"));
		FlowPane showTimeline = loader.load();
		Stage stage2 = new Stage();
        stage2.setScene(new Scene(showTimeline));  
        stage2.setTitle("Timelines");
        stage2.show();	
        
        System.out.println("showtimeline");
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
	

	public static void test() throws IOException{
		FXMLLoader loader = new FXMLLoader(Main.class.getResource("StartingMode.fxml"));
        gui.StartingModeController myController = loader.getController();
        myController.createTimeline();       
	}

	public static void main(String[] args) {
		launch(args);
	}
}