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
			if (EndDate.getValue().isAfter(StartDate.getValue()) ){
				if (TimelineName.getText().isEmpty()){
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
	
	public Line verticalLine(int size) {
		Line timeLine = new Line(50,0,50,size);
		timeLine.setStrokeWidth(3);
		return timeLine;
	}
	
	public Line clickAbleHline(int size) {
		Line timeLine = new Line(0, 50, size, 50);
		timeLine.setStrokeWidth(5);
		timeLine.setOnMouseClicked(e ->{
			try {
				Main.showEventHandler();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		return timeLine;
	}
	
	public Line Hline(int size){
		Line timeLine = new Line(0, 50, size, 50);
		timeLine.setStrokeWidth(5);
		return timeLine;
	}
	
	public Pane generateTimeL(){
		Pane pane = new Pane();
		HBox hbox = new HBox();
		for(int i = 0; i < 5; i++) {
			hbox.getChildren().addAll(verticalLine(100),Hline(250));
		}
		
		hbox.getChildren().add(verticalLine(100));
		hbox.setLayoutX(5);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().add(hbox);
		
		return pane;
	}
	
	public Pane zoomedTimeline() {
		Pane pane = new Pane();
		HBox hbox = new HBox();
		for(int j = 0; j<5; j++){
			hbox.getChildren().addAll(verticalLine(100),Hline(12));
		for(int i = 0; i < 12; i++) {	
			hbox.getChildren().addAll(verticalLine(50),Hline(12));
		}
		}
		hbox.getChildren().addAll(Hline(10), verticalLine(12));
		hbox.setLayoutX(5);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().add(hbox);
		
		return pane;	
	}
	
	/**
	 * Calculation month for how long the timeline is
	 * @return timeline length
	 */
	public int dayCounter() {
		String start = StartDate.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String end = EndDate.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		String startMonth = start.substring(0,4);
		String endMonth = end.substring(0,4);
		String startDay = start.substring(5, 6);
		String endDay = end.substring(5, 6);
		
		int startCalc = (Integer.parseInt(startMonth) * 12) + Integer.parseInt(startDay);
		int endCalc = (Integer.parseInt(endMonth) * 12) + Integer.parseInt(endDay);
		
		return endCalc - startCalc;
	}
}
