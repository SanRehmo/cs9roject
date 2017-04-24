package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import gui.Main;


public class CreateModeController {
	
	@FXML
    private TextField TimelineName;

    @FXML
    private DatePicker StartDate;

    @FXML
    private DatePicker EndDate;

    @FXML
    private CheckBox OnlyYears;

    @FXML
    private Button CreateButton;
	
	@FXML
	private void Test() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("StartingMode.fxml"));
		StartingModeController myController = loader.getController();
        myController.createTimeline();
	}
	
	public Line HoricontellLine() {
		Line timeLine = new Line(50,10,50,100);
		timeLine.setStrokeWidth(3);
		return timeLine;
	}
	
	public Line VerticalLine() {
		Line timeLine = new Line(0, 50, 100, 50);
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
	
	public Pane generatTimeLine() {
		Pane pane = new Pane();
		HBox hbox = new HBox();
		for(int i = 0; i < 10; i++) {
			HoricontellLine().setLayoutY(50);
			VerticalLine().setLayoutY(50);
			hbox.getChildren().addAll(HoricontellLine(),VerticalLine());
		}
		
		hbox.getChildren().add(HoricontellLine());
		hbox.setLayoutX(5);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().add(hbox);
		
		return pane;
		
	}

}
