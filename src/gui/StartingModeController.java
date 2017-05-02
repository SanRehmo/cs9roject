package gui;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import cs_9roject.Project;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import gui.Main;

public class StartingModeController {

	  
	
	@FXML
	private ScrollPane start_scrollpane;
	
	
	@FXML
    private Button helpButton;
	
	
	
	/**
     * Method for displaying the createMode window.
     */
	public void createMode() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("CreateMode.fxml"));
		FlowPane createMode = loader.load();
		CreateModeController c = (CreateModeController) loader.getController();
		c.primaryScrollpane = start_scrollpane;
			
		Stage stage = new Stage();
	    stage.setScene(new Scene(createMode));  
	    stage.setTitle("CreateMode");
	    stage.show();	 
	    
	}
	
	
	/**
     * Method for displaying Timelines to show window.
     */
	@FXML
	private void showTimeline() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("showTimelines.fxml"));
		FlowPane showTimeline = loader.load();
		showTimelinesController c = (showTimelinesController) loader.getController();
		c.primaryScrollpane = start_scrollpane;
		
		Stage stage2 = new Stage();
	    stage2.setScene(new Scene(showTimeline));  
	    stage2.setTitle("Timelines");
	    stage2.show();	
	}
	
	/**
     * Method for displaying all projects in database to load one of them.
     */
	@FXML
	private void loadProject(){
		// List <Project> projects = new ArrayList <Project>();
		// projects.add(Main.dao.load(3));
		List <Project> projects = Main.dao.loadAllProjects();
		if (projects.size()>0){
			ChoiceDialog<Project> choiceDialog = new ChoiceDialog<Project>(projects.get(0), projects);
			choiceDialog.setTitle("Choice Dialog");
			choiceDialog.setHeaderText("Load project");
			choiceDialog.setContentText("Choose your project:");

			// Traditional way to get the response value.
			Optional<Project> result = choiceDialog.showAndWait();
			if (result.isPresent()){
			    Main.project=result.get();
			}

		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INFORMATION!");
			alert.setHeaderText("Cannot load project!");
			alert.setContentText("Database are empty!");
			alert.showAndWait();
		}
	}
	
	/**
     * Method for displaying Eventhandler the window, this window will later be displayed by clicking on the timeline and not on the button "EventHandler"
     */
	@FXML
	private void showEventHandler() throws IOException{
		if (Main.project.getTimelines().size()==0){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot add event!");
			alert.setContentText("You should have att least 1 timeline to add events");
			alert.showAndWait();
			return;
		}
		Main.showEventHandler();
	}
	
}