package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import cs_9roject.Event;
import cs_9roject.Project;
import cs_9roject.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import gui.Main;


public class CreateModeController {

	public ScrollPane primaryScrollpane;

	@FXML
    public TextField TimelineName;

    @FXML
    public DatePicker StartDate;

    @FXML
    public DatePicker EndDate;

    @FXML
    public Button CreateButton;
    
    @FXML
    public Button deleteButton;
        
    VBox vbox = new VBox();
    
    eventHandlerController alert = new eventHandlerController();
    public int TimelineID = StartingModeController.timelineIdToModify;
	    
    
    @FXML
    public void initialize() {
    	if (TimelineID!=0){
    		StartDate.setDayCellFactory(dayCellFactoryStartDate);
    		EndDate.setDayCellFactory(dayCellFactoryEndDate);
    	}
    }
    
	@FXML
	private void addTimeline() throws IOException{
		// Check if start and end date are selected
		if (StartDate.getValue()!=null && EndDate.getValue()!=null){
			// Check if end date is after or equals start date
			if (!StartDate.getValue().isAfter(EndDate.getValue())){
				// Check if the user inputs timeline's name
				if (TimelineName.getText().replaceAll("\\s+","").isEmpty()){
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Please confirm");
					alert.setHeaderText("You are going to create a new timeline without a name!");
					alert.setContentText("Are you sure you like to proceed?");
					if (alert.showAndWait().get() == ButtonType.CANCEL){
						return;
					}
				}
				// if all inputs was correct then add timeline
				Timeline temp = new Timeline(StartDate.getValue(),EndDate.getValue(),TimelineName.getText(), false );
				if (TimelineID == 0){
				    Main.project.addTimeline(temp);
				    CheckBox cbi = new CheckBox( temp.getTitle() +" (" + temp.getEvents().size() +" event/s) ID: " + temp.getTimelineId() );
			    	cbi.setSelected(true);			    	
			    	showTimelinesController.timelines.add(cbi);
				}
				else {
					temp.setTimelineId(Main.project.getTimeline(TimelineID).getTimelineId());
					temp.events=Main.project.getTimeline(TimelineID).getEvents();
					Main.project.modifyTimeline(TimelineID, temp);
				}
			    Stage stage = (Stage) CreateButton.getScene().getWindow();
			    stage.close();
			}
			else {
				alert.alertWindow(AlertType.ERROR, "ERROR!", "Cannot add timeline!", "End date should be after or equal to start date");
			}	
		}
		else{
			alert.alertWindow(AlertType.ERROR, "ERROR!", "Cannot add timeline!", "End or Start time is not selected");
		}
    }
	
	@FXML
	private void removeTimeline() throws IOException{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Please confirm");
		alert.setHeaderText("You are going to delete the timeline: "+Main.project.getTimeline(TimelineID).getTitle()+" ID: "+Main.project.getTimeline(TimelineID).getTimelineId());
		alert.setContentText("Are you sure you like to proceed?");
		if (alert.showAndWait().get() == ButtonType.OK){
			showTimelinesController.timelines.remove(Main.project.getTimelines().indexOf(Main.project.getTimeline(TimelineID)));
			Main.project.removeTimeline(TimelineID);			
			Stage stage = (Stage) CreateButton.getScene().getWindow();
		    stage.close();
		}	
	}
	
	
	final Callback<DatePicker, DateCell> dayCellFactoryStartDate = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					List<Event> events = Main.project.getTimeline(StartingModeController.timelineIdToModify).getEvents();
					for (Event e : events)
						if (item.isAfter(e.getStartTime().toLocalDate()))
							this.setDisable(true);
				}	
			};
		}
	};
	final Callback<DatePicker, DateCell> dayCellFactoryEndDate = new Callback<DatePicker, DateCell>() {
		public DateCell call(final DatePicker datePicker) {
			return new DateCell() {
				@Override
				public void updateItem(LocalDate item, boolean empty) {
					super.updateItem(item, empty);
					List<Event> events = Main.project.getTimeline(StartingModeController.timelineIdToModify).getEvents();
					for (Event e : events)
						if (e.isDurationEvent() && item.isBefore(e.getEndTime().toLocalDate()))
							this.setDisable(true);
						else if (!e.isDurationEvent() && item.isBefore(e.getStartTime().toLocalDate()))
							this.setDisable(true);
				}	
			};
		}
	};
}
