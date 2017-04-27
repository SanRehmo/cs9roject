package gui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;


public class showTimelinesController {
	
	public ScrollPane primaryScrollpane;

	List<CheckBox> timelines = new ArrayList<CheckBox>();


	VBox vbox = new VBox();
	
	VBox scrollBox = new VBox();
	
	 @FXML
	 public void initialize() {  //Reading every timeline and print there names in checkboxes
		 for(int i=0; i<Main.project.getTimelines().size(); i++){
			 HBox hbox = new HBox();
			 Pane pane = new Pane();
			 CheckBox cbi = new CheckBox( Main.project.getTimeline(i).getTitle() +" ( " + Main.project.getTimeline(i).getEvents().size() +" event(s) )" );
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
			scrollBox.getChildren().addAll(yearShow(i),generateTimeL(i));
			}
			
		}
		else{
			for(int i=0; i<timelines.size(); i++){
				if(timelines.get(i).isSelected()){
				scrollBox.getChildren().addAll(yearShow(i),generateTimeL(i));
				}	
			}	 
		}
		
	// scrollBox.getChildren().add(yearShow(Main.project.getTimeline(0).getStartDate(),Main.project.getTimeline(0).getEndDate()));     Shows years of first timeline
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
		
		return timeLine;
	}
	
	public Pane generateTimeL(int id){
		Text title = new Text();
		title.setText(Main.project.getTimeline(id).getTitle());
		title.setFont(Font.font ("Verdana", 20));
		
		int size = (int) title.getBoundsInLocal().getWidth();
		
		//Name length represent by rectangle.
		Rectangle rectangle = new Rectangle(size + 50, 20);
		rectangle.setFill(Color.TRANSPARENT);
		
				
		Pane pane = new Pane();
		HBox hbox = new HBox();
		hbox.getChildren().add(rectangle);
		for(int i = 0; i < 5; i++) {
			hbox.getChildren().addAll(verticalLine(100),Hline(250));
		}
		
		hbox.getChildren().addAll(verticalLine(100));
		hbox.setLayoutX(5);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(hbox);
		
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
	
	public int yearCounter(LocalDate StartDate, LocalDate EndDate) {
		  return EndDate.getYear() - StartDate.getYear();
		 }
	
	public int monthCounter(LocalDate StartDate, LocalDate EndDate) {
	  
	  int startMonth = StartDate.getMonthValue();
	  int endMonth = EndDate.getMonthValue();
	  
	  int startYear = StartDate.getYear();
	  int endYear = EndDate.getYear();
	  
	  int temp = endYear - startYear;
	  
	  if(temp<1){
		  temp += (endMonth - startMonth)%12 -(endYear - startYear);
	  }
	  else{
		  temp += (endMonth - startMonth)%12 -(endYear - startYear)+temp*12;
	  }
	  
	  return temp;

	 }
	
	public Pane yearShow(int id) {
		
		  LocalDate startDate = Main.project.getTimeline(id).getStartDate();
		  LocalDate endDate = Main.project.getTimeline(id).getEndDate();
		  
		  Pane pane = new Pane();
		  
		  HBox yearBox = new HBox();
		  
		  Rectangle rectangle = new Rectangle(45, 20);
		  rectangle.setFill(Color.TRANSPARENT);
		  
		  Text title = new Text();
		  title.setText(Main.project.getTimeline(id).getTitle());
		  title.setFont(Font.font ("Verdana", 20));
		  yearBox.getChildren().addAll(title,rectangle);  
	
		  for(int i = 0; i < Math.floor((yearCounter(startDate,endDate)+(yearCounter(startDate,endDate)/5))); i+=Math.floor(yearCounter(startDate,endDate)/5)) {
		  Rectangle rec = new Rectangle(228, 20);
		  rec.setFill(Color.TRANSPARENT);
	      String temp =String.valueOf(startDate.getYear()+i); //- startDate.getYear()));
		  Text text = new Text();
		  text.setText(temp);	  
		  yearBox.getChildren().addAll(text,rec);
		  }
		  
		  pane.getChildren().add(yearBox);
		  
		  return pane;
		 }
	
}
