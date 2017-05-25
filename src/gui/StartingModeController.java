package gui;


import cs_9roject.Event;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class StartingModeController {

	TimelinesDAO dao = new TimelinesDAO();
	  
	
	@FXML
	public ScrollPane start_scrollpane;
	
	@FXML
    private BorderPane StartBorderPane;
	
	@FXML
    private Button helpButton;
	
	@FXML
    private Button new_btn;
	
	@FXML
    private Button save_btn;
	
	@FXML
    private Button delete_btn;
	
	@FXML
    private Label name_label;
	
	
	public static int timelineIdToModify=0;
	public static int eventIdToModify=0;
	

	public void initialize() {
		name_label.setText(Main.project.projectName);
		name_label.setStyle("-fx-background-color: " + "white");
		name_label.setOnMouseClicked(event -> {modifyProject();});
	}
	
	/**
     * Method to open add.
     */
	public void addTimeline(){
		
	}
	
	/**
     * Method for displaying the createMode window.
     */
	public void createMode() throws IOException{
		timelineIdToModify=0;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("CreateMode.fxml"));
		FlowPane createMode = loader.load();
		CreateModeController c = (CreateModeController) loader.getController();
		c.primaryScrollpane = start_scrollpane;
		Stage stage = new Stage();
	    stage.setScene(new Scene(createMode));  
	    stage.setTitle("Create new timeline");
	    stage.showAndWait();
	    refreshTimeline();
		
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
     * Method for refresh Timelines to show window.
     */
	public void refreshTimeline() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("showTimelines.fxml"));
		loader.load();
		showTimelinesController c = (showTimelinesController) loader.getController();
		c.primaryScrollpane = start_scrollpane;
		c.primaryBorderpane = StartBorderPane;
		c.refreshTimeline();
	}
	
	/**
     * Method for create new project.
     */
	@FXML
	private void newProject(){
		if (Main.project.getTimelines().size()>0){
			if (alertWindow(AlertType.CONFIRMATION, "Please confirm", "All changes are deleted. Please save each change before proceeding!", "Are you sure you like to proceed?")== ButtonType.CANCEL)
				return;
		}
		Main.project=new Project();
		showTimelinesController.timelines.removeAll(showTimelinesController.timelines);
		Event.setCount(dao.getHighestEventID() + 1);
		Timeline.setCount(dao.getHighestTimelineID() + 1);
		Main.project.ProjectID =(dao.getHighestProjectID() + 1);
		Main.project.projectName="Project " + Main.project.ProjectID;
		Project.setCount(Main.project.ProjectID + 1);
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
		modifyProject();
		name_label.setText(Main.project.projectName);
		delete_btn.setDisable(true);
	    start_scrollpane.setContent(new VBox());
	}
	
	/**
     * Method for displaying all projects in database to load one of them.
	 * @throws IOException 
     */
	@FXML
	private void loadProject() throws IOException{
		if (Main.project.getTimelines().size()>0){
			if (alertWindow(AlertType.CONFIRMATION, "Please confirm", "All changes are deleted. Please save each change before proceeding!", "Are you sure you like to proceed?")== ButtonType.CANCEL)
				return;
		}

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
			    name_label.setText(Main.project.projectName);
			    delete_btn.setDisable(false);
			    start_scrollpane.setContent(new VBox());
			    showTimelinesController.timelines.removeAll(showTimelinesController.timelines);
			    for(int i=0; i<Main.project.getTimelines().size(); i++){
			    	CheckBox cbi = new CheckBox( Main.project.getTimelines().get(i).getTitle() +" (" + Main.project.getTimelines().get(i).getEvents().size() +" event/s) ID: " + Main.project.getTimelines().get(i).getTimelineId() );
			    	cbi.setSelected(true);			    	
			    	showTimelinesController.timelines.add(cbi);
			    }
			    refreshTimeline();
			}

		}
		else{
			alertWindow(AlertType.INFORMATION, "INFORMATION!", "Cannot load project!", "Database is empty!");
		}
		
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
	}

	/**
	 * Method for deleting Project.
	 */
	@FXML
	private void deleteProject() throws IOException {
		if (alertWindow(AlertType.CONFIRMATION, "Please confirm", "You are going to delete the project: "+Main.project.projectName+" ID: "+Main.project.ProjectID, "Are you sure you like to proceed?")== ButtonType.OK) {
			delete_btn.setDisable(false);
			dao.delete(Main.project);
			Main.project = new Project();
			showTimelinesController.timelines.removeAll(showTimelinesController.timelines);
			start_scrollpane.setContent(new VBox());
			delete_btn.setDisable(true);
		}
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
	}


	/**
	 * Method for saving Project.
	 */
	@FXML
	private void saveProject() throws IOException {

		if (Main.project.getTimelines().size()>0){
			if (dao.exists(Main.project.ProjectID)){
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Save project");
				alert.setHeaderText("What do you want the program to do?");
				alert.setContentText("Please select:");

				ButtonType buttonTypeOverwrite = new ButtonType("Overwrite");
				ButtonType buttonTypeSaveCopy = new ButtonType("Save Copy");
				ButtonType buttonTypeCancel = new ButtonType("Cancel");

				alert.getButtonTypes().setAll(buttonTypeOverwrite, buttonTypeSaveCopy, buttonTypeCancel);
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeOverwrite){
					if (dao.save(Main.project))
						alertWindow(AlertType.INFORMATION, "Saving", "Successfully saved", null);
					else
						alertWindow(AlertType.INFORMATION, "Saving", "Saving failed", null);
					delete_btn.setDisable(false);
				}
				else if (result.get() == buttonTypeSaveCopy){
					Project p = new Project();
					Main.project.ProjectID=p.ProjectID;
					Main.project.projectName=Main.project.projectName+ "-Copy";
					for (Timeline t : Main.project.getTimelines()){
						t.setTimelineId(new Timeline().getTimelineId());
						for (Event e : t.getEvents())
							e.setEventId(new Event(e).getEventId());
					}
					if (dao.save(Main.project))
						alertWindow(AlertType.INFORMATION, "Saving", "Successfully saved", null);
					else
						alertWindow(AlertType.INFORMATION, "Saving", "Saving failed", null);
					delete_btn.setDisable(false);
				}
			}
			else{
				if (dao.save(Main.project))
					alertWindow(AlertType.INFORMATION, "Saving", "Successfully saved", null);
				else
					alertWindow(AlertType.INFORMATION, "Saving", "Saving failed", null);
				delete_btn.setDisable(false);
			}
			
		}
		else{
			alertWindow(AlertType.ERROR, "ERROR!", "Nothing to save!" , "To save a project, it should have at least 1 timeline.");
		}
		
		System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
	}
	
	/**
	 * Method for modify Project's name.
	 */
	@FXML
	private void modifyProject() {
		TextInputDialog dialog = new TextInputDialog(Main.project.projectName);
		dialog.setTitle("Change project's name");
		dialog.setHeaderText("Change project's name");
		dialog.setContentText("Please enter a new name:");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !result.get().replaceAll("\\s+","").isEmpty()){
			Main.project.projectName = result.get();
			name_label.setText(Main.project.projectName);
			System.out.println("Current project: "+ Main.project.projectName+" ID: "+ Main.project.ProjectID);
		}
	}

	/**
     * Method for displaying Eventhandler the window, this window will later be displayed by clicking on the timeline and not on the button "EventHandler"
     */
	@FXML
	private void showEventHandler() throws IOException{
			if (Main.project.getTimelines().size()==0){
			alertWindow(AlertType.ERROR, "ERROR!", "Cannot add event!", "You should have at least 1 timeline to add events");
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
			    eventIdToModify=0;
			    Main.showEventHandler();
			    refreshTimeline();
			}
		}
	}
	
	public void displayInformation() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText("Hej, we are happy that you use the timeline manager. \nJust start creating a new project by clicking the appropriate button in the menu. In the next step you need to add a timeline(s) and belonging events to your project - do not forget your appointments any longer!!\nIf you need more help, have a look to our manual: https://goo.gl/i80Qdm");
		alert.showAndWait();
	}
	
	public ButtonType alertWindow(AlertType type, String Title, String headText, String contentText) {
    	Alert alert = new Alert(type);
		alert.setTitle(Title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		return alert.showAndWait().get();
    	
    }
}