package gui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class showTimelinesController {
	
	public ScrollPane primaryScrollpane;

	List<CheckBox> timelines = new ArrayList<CheckBox>();


	VBox vbox = new VBox();
	
	VBox scrollBox = new VBox();
	
	 @FXML
	    public void initialize() {  //Reading every timeline and print there names in checkboxex 		 
		 for(int i=0; i<Main.project.getTimelines().size(); i++){
			 HBox hbox = new HBox();
			 Pane pane = new Pane();
			 CheckBox cbi = new CheckBox( Main.project.getTimeline(i).getTitle());
			 timelines.add(cbi);
			 hbox.getChildren().addAll(cbi);
			 hbox.setLayoutX(10);
			 hbox.setLayoutY(5);
			 hbox.setAlignment(Pos.CENTER);	
			 pane.getChildren().add(hbox);	 
		  	 vbox.getChildren().add(pane);
		 }
		 show_scrollpane.setContent(vbox);
	    }
	

	@FXML
	private ScrollPane show_scrollpane;

	@FXML
	private Button doneButton;

	@FXML
	private CheckBox displayAll;
	
	@FXML
	public void showTimeline(){
		if(displayAll.isSelected()){
			for(int i=0; i<timelines.size(); i++){
			scrollBox.getChildren().add(generateTimeL(Main.project.getTimeline(i).getTitle()));
			}
			
		}
		else{
			for(int i=0; i<timelines.size(); i++){
				if(timelines.get(i).isSelected()){
				scrollBox.getChildren().add(generateTimeL(Main.project.getTimeline(i).getTitle()));
				System.out.println(dayCounter(Main.project.getTimeline(i).getStartDate(),Main.project.getTimeline(i).getEndDate()));
				}
			
			}	 
		}
		
	primaryScrollpane.setContent(scrollBox);
	 Stage stage = (Stage) doneButton.getScene().getWindow();
	 
	 stage.close();	
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
	
	public Pane generateTimeL(String Timelinename){
		Text title = new Text();
		title.setText(Timelinename);
		Pane pane = new Pane();
		HBox hbox = new HBox();
		hbox.getChildren().add(title);
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
	/*public int dayCounter(LocalDate StartDate, LocalDate EndDate) {
		String start = StartDate.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String end = EndDate.getValue().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		String startMonth = start.substring(0,4);
		String endMonth = end.substring(0,4);
		String startDay = start.substring(5, 6);
		String endDay = end.substring(5, 6);
		
		int startCalc = (Integer.parseInt(startMonth) * 12) + Integer.parseInt(startDay);
		int endCalc = (Integer.parseInt(endMonth) * 12) + Integer.parseInt(endDay);
		
		return endCalc - startCalc;
	}*/
    
	public int dayCounter(LocalDate StartDate, LocalDate EndDate) {
		int start = StartDate.getMonthValue();
		int end = EndDate.getMonthValue();
		
		/*String startMonth = start.substring(0,4);
		String endMonth = end.substring(0,4);
		String startDay = start.substring(5, 6);
		String endDay = end.substring(5, 6);
		*/
		System.out.println(start);
		System.out.println(end);
		
		return start - end;
    
	}
}
