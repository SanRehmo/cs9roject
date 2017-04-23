package gui;


import java.io.IOException;

import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import gui.Main;

public class StartingModeController {

	Stage newStage = new Stage();
	
	@FXML
	private ScrollPane start_scrollpane;
	
	
	@FXML
    private Button helpButton;
	
	@FXML
	public void createMode() throws IOException{
		Main.showCreateMode();
		System.out.print("Testtest");
		
	}
	
	@FXML
	private void showTimeline() throws IOException{
		Main.showShowTimeline();
	}
	
	VBox vbox = new VBox();
	
	@FXML
	public void createTimeline() throws IOException{
		
		vbox.getChildren().addAll(generatTimeLine());			
		
		start_scrollpane.setContent(vbox); 
		
	} 
	
	public void ScrollPane(ScrollPane x) {
        this.start_scrollpane = x;
   }
	
	
	public ScrollPane getPane(){
		return start_scrollpane;
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