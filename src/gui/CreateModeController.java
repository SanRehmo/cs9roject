package gui;

import java.io.IOException;

import cs_9roject.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import gui.Main;


public class CreateModeController {

	public ScrollPane primaryScrollpane;

	@FXML
    public TextField TimelineName;

    @FXML
    private DatePicker StartDate;

    @FXML
    private DatePicker EndDate;

    @FXML
    private CheckBox OnlyYears;

    @FXML
    private Button CreateButton;
        
    VBox vbox = new VBox();
    
    eventHandlerController alert = new eventHandlerController();
	    
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
					alert.setHeaderText("You are going to create new timeline without name!");
					alert.setContentText("Are you sure you like to proceed?");
					if (alert.showAndWait().get() == ButtonType.CANCEL){
						return;
					}
				}
				// if all inputs was correct then add timeline
				Timeline temp = new Timeline(StartDate.getValue(),EndDate.getValue(),TimelineName.getText(), OnlyYears.isSelected() );
			    Main.project.addTimeline(temp);
			    Stage stage = (Stage) CreateButton.getScene().getWindow();
			    stage.close();
			}
			else {
				alert.alertWindow(AlertType.ERROR, "ERROR!", "Cannot add timeline!", "End date should be after or equals start date");
			}	
		}
		else{
			alert.alertWindow(AlertType.ERROR, "ERROR!", "Cannot add timeline!", "End or Start time is not selected");
		}
    }
}
