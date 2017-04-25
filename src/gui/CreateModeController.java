package gui;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import cs_9roject.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
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
	    
	@FXML
	private void addTimeline() throws IOException{
		if (StartDate.getValue()!=null && EndDate.getValue()!=null){
			if (EndDate.getValue().isAfter(StartDate.getValue()) || EndDate.getValue().equals(StartDate.getValue()) ){
				if (TimelineName.getText().replaceAll("\\s+","").isEmpty()){
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Please confirm");
					alert.setHeaderText("You are going to create new timeline without name!");
					alert.setContentText("Are you sure you like to proceed?");
					if (alert.showAndWait().get() == ButtonType.CANCEL){
						return;
					}
				}
				Timeline temp = new Timeline(StartDate.getValue(),EndDate.getValue(),TimelineName.getText(), OnlyYears.isSelected() );
			    Main.project.addTimeline(temp);
			    Stage stage = (Stage) CreateButton.getScene().getWindow();
			    stage.close();
			}
			else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR!");
				alert.setHeaderText("Cannot add timeline!");
				alert.setContentText("End time should be after start time");
				alert.showAndWait();
			}	
		}
		else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot add timeline!");
			alert.setContentText("End or Start time is not selected");
			alert.showAndWait();
		}
    }
	
}
