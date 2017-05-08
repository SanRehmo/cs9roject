package gui;


import cs_9roject.Project;
import cs_9roject.Timeline;
import cs_9roject.TimelinesDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StartingModeController {

	TimelinesDAO dao = new TimelinesDAO();
	  
	
	@FXML
	private ScrollPane start_scrollpane;
	
	@FXML
    private BorderPane StartBorderPane;
	
	@FXML
    private Button helpButton;
	
	@FXML
    private Button delete_btn;
	
	public static int timelineIdToModify=0;
	public static int eventIdToModify=22;
	
	
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
		c.primaryBorderpane = StartBorderPane;
		
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
			    delete_btn.setDisable(false);
			}

		}
		else{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("INFORMATION!");
			alert.setHeaderText("Cannot load project!");
			alert.setContentText("Database are empty!");
			alert.showAndWait();
		}
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
	}

	/**
	 * Method for deleting Project.
	 */
	@FXML
	private void deleteProject() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Please confirm");
		alert.setHeaderText("You are going to delete the project: "+Main.project.projectName+" ID: "+Main.project.ProjectID);
		alert.setContentText("Are you sure you like to proceed?");
		if (alert.showAndWait().get() == ButtonType.OK){
			dao.delete(Main.project);
			Main.project = new Project();
		}
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
	}


	/**
	 * Method for saving Project.
	 */
	@FXML
	private void saveProject() throws IOException {
		// Main.project.getTimelines().get(Main.project.getTimelines().size()-1).setTimelineId(dao.getHighestTimelineID());
		dao.save(Main.project);
		delete_btn.setDisable(false);
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
		else {
			ChoiceDialog<Timeline> choiceDialog = new ChoiceDialog<Timeline>(Main.project.getTimelines().get(0), Main.project.getTimelines());
			choiceDialog.setTitle("Choice Dialog");
			choiceDialog.setHeaderText("Select a Timeline to add the event");
			choiceDialog.setContentText("Select a timeline:");

			// Traditional way to get the response value.
			Optional<Timeline> result = choiceDialog.showAndWait();
			if (result.isPresent()){
			    timelineIdToModify= result.get().getTimelineId();
			    Main.showEventHandler();
			}
		}
	}
	
}