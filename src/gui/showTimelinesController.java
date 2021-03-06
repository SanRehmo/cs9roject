package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import cs_9roject.Event;
import cs_9roject.NonDurationEvent;
import cs_9roject.Timeline;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;

import javafx.util.Callback;
import gui.CreateModeController;
import gui.Main;
import gui.StartingModeController;

@SuppressWarnings("restriction")
public class showTimelinesController {
	public int timelineId = 0;
	
	public ScrollPane primaryScrollpane;
	
	public BorderPane primaryBorderpane; 

	public static List<CheckBox> timelines = new ArrayList<CheckBox>();
	
	VBox vbox = new VBox();
	
	VBox scrollBox = new VBox();
		
	 @FXML
	 public void initialize() {  //Reading every timeline and print there names in checkboxes
		 
		 // If one of timelines not selected, disable displayAll
		 for (CheckBox c : timelines){
			 displayAll.setSelected(displayAll.isSelected() && c.isSelected());
			 // check again every the user click on CheckBox
			 c.setOnMouseClicked(event ->{
				 displayAll.setSelected(true);
				 for (CheckBox ch : timelines){
					 displayAll.setSelected(displayAll.isSelected() && ch.isSelected());
				 }
			 });			 
		 }
		 
		 displayAll.setOnMouseClicked(event ->{
			 for (CheckBox c : timelines)
				 c.setSelected(displayAll.isSelected());
		 });
		 
		 for(int i=0; i<Main.project.getTimelines().size(); i++){
			 HBox hbox = new HBox();
			 Pane pane = new Pane();
			 hbox.getChildren().addAll(timelines.get(i));
			 hbox.setLayoutX(10);
			 hbox.setLayoutY(5);
			 hbox.setAlignment(Pos.CENTER);	
			 pane.getChildren().add(hbox);	 
		  	 vbox.getChildren().add(pane);
		 }

		 show_scrollpane.setContent(vbox);
	    }
	

	@FXML
	private ScrollPane show_scrollpane; //ScrollPane from starting window

	@FXML
	private Button doneButton;

	@FXML
	private CheckBox displayAll;
	
	
	/**
     * Showing the selected timelines in the scrollPane.
     */
	@FXML
	public void showTimeline(){		//Method that is showing the timelines in the scrollPane
		
		for(int i=0; i<timelines.size(); i++){	//Just displaying the checked timelines
			if(timelines.get(i).isSelected()){
			scrollBox.getChildren().addAll(yearShow(Main.project.getTimelines().get(i).getTimelineId(),Main.project.getTimelines().get(i).getStartDate(),Main.project.getTimelines().get(i).getEndDate().plusYears(1)),generateTimeL(Main.project.getTimelines().get(i).getTimelineId(), Main.project.getTimelines().get(i).getStartDate(), Main.project.getTimelines().get(i).getEndDate().plusYears(1)),spaceBetween());
			}	
		}
		primaryScrollpane.setContent(scrollBox);
		Stage stage = (Stage) doneButton.getScene().getWindow();
		stage.close();		
	}
	
	/**
     * Refresh the timelines that is already in the scrollPane.
     */
	public void refreshTimeline(){	//Method that is showing the timelines in the scrollPane
		scrollBox.getChildren().removeAll(scrollBox.getChildren());
		
		if(displayAll.isSelected()){//If DisplayAll is selected the program will show every timeline
			for (CheckBox c : timelines)
				c.setSelected(true);
			
		}
		for(int i=0; i<timelines.size(); i++){	//Just displaying the checked timelines
			if(timelines.get(i).isSelected()){
			scrollBox.getChildren().addAll(yearShow(Main.project.getTimelines().get(i).getTimelineId(),Main.project.getTimelines().get(i).getStartDate(),Main.project.getTimelines().get(i).getEndDate().plusYears(1)),generateTimeL(Main.project.getTimelines().get(i).getTimelineId(), Main.project.getTimelines().get(i).getStartDate(), Main.project.getTimelines().get(i).getEndDate().plusYears(1)),spaceBetween());
			}	
		}
		primaryScrollpane.setContent(scrollBox);
	}

	/**
	 * Vertical line for the regular timeLine, not clickable. 
	 * @param size of the line.
	 * @return vertical line
	 */
	public Line verticalLine(int size) {		//Making every vertical lines in a timeline
		Line timeLine = new Line(50,0,50,size);
		timeLine.setStrokeWidth(3);
		return timeLine;
	}
	
	/**
	 * Vertical line for the zoomed timeline.
	 * @param size of the line
	 * @param startDate of the vertical line, when zoomed.
	 * @param id of timeline
	 * @return a clickable vertical line.
	 */
	public Line KlickverticalLine(int size, LocalDate startDate , int id) {		//Making every vertical lines in a timeline
		Line timeLine = new Line(50,0,50,size);
		timeLine.setStrokeWidth(3);
		int j = 0;
//		Checks if there is an event in that month and year for the timeline coloring.
		for(int i = 0; i < Main.project.getTimeline(id).getEvents().size(); i++) {
			if(Main.project.getTimeline(id).getEvents().get(i).getStartTime().getMonth().toString().equals(startDate.getMonth().toString())
					&& Main.project.getTimeline(id).getEvents().get(i).getStartTime().getYear() == startDate.getYear()) {
				timeLine.setStyle("-fx-stroke: "+Main.project.getTimeline(id).getEvents().get(i).getColorName()+";");
				

				j++;
				Tooltip.install(timeLine, new Tooltip(j + " events"));
				
			}
			
		}
		j = 0; 
		
		dp.valueProperty().addListener((ov, oldValue, newValue) -> {
			if (dp.getValue()!=null && Main.project.getTimeline(id)!=null){
				int j2=0;
				for(int i = 0; i < Main.project.getTimeline(id).getEvents().size(); i++) {
					if(Main.project.getTimeline(id).getEvents().get(i).getStartTime().getMonth().toString().equals(startDate.getMonth().toString())
							&& Main.project.getTimeline(id).getEvents().get(i).getStartTime().getYear() == startDate.getYear()) {
						timeLine.setStyle("-fx-stroke: "+Main.project.getTimeline(id).getEvents().get(i).getColorName()+";");
						

						j2++;
						Tooltip.install(timeLine, new Tooltip(j2 + " events"));
						
					}
				}
				if (j2==0) {
					timeLine.setStyle("-fx-stroke: BLACK;");
					disableIfOutTimeline (timeLine, startDate, id );
				}
				j2=0;
			}
        });

		timeLine.setOnMouseClicked(e -> {
			showInfoByMonth (0, startDate.getYear(), startDate.getMonthValue(), timeLine);
		});
			primaryScrollpane.setContent(scrollBox);

		disableIfOutTimeline (timeLine, startDate, id );
	
		return timeLine;
	}
	
	
	private void disableIfOutTimeline (Line line, LocalDate startDate, int id ){
		Timeline t = Main.project.getTimeline(id);
			if(startDate.isBefore(t.getStartDate()) || startDate.isAfter(t.getEndDate())) {
				line.setStyle("-fx-stroke: " + " LightGray;");
				line.setOnMouseClicked(e -> {});
			}
	}
	
	static DatePicker dp = new DatePicker() ;
	
	/**
	 * Clickable horizontal lines for zoomed timeline.
	 * @param size of the line.
	 * @param id of the timeline.
	 * @param counter 
	 * @param startDate of the zoomed in year.
	 * @param endDate of the zoomed in year.
	 * @return horizontal line.
	 */
	public Line clickAbleHline(int size, int id, int counter, LocalDate startDate, LocalDate endDate) {	//Making a horizontal line that will open zoomedTimeline when you press the line
		Line timeLine = new Line(0, 50, size, 50);
		timeLine.setStrokeWidth(5);
		
		
				
		int years;
		int Ycounter;
		
		
		if(yearCounter(startDate,endDate)%5 == 0){
			Ycounter = (int)(yearCounter(startDate,endDate)/5);
		}
		else{
			years = (int)((yearCounter(startDate,endDate)-(yearCounter(startDate,endDate)%5)));
			Ycounter = years - years%4;
			Ycounter = Ycounter/4;	
			
		}

		if(Ycounter == 0)
			Ycounter ++;
		int FinalCounter = Ycounter;
		
		//Adds color to the line depending of the color of the last event in that calender month.
		for(int i = 0; i < Main.project.getTimeline(id).getEvents().size(); i++) {
			for(int j = 0; j<FinalCounter; j++){
				if(Main.project.getTimeline(id).getEvents().get(i).getStartTime().getYear() == (startDate.getYear()+FinalCounter*counter+j)) {
					timeLine.setStyle("-fx-stroke: "+Main.project.getTimeline(id).getEvents().get(i).getColorName()+";");
				}	
			}
		}
		
		dp.valueProperty().addListener((ov, oldValue, newValue) -> {
				if (dp.getValue()!=null && Main.project.getTimeline(id)!=null){
				timeLine.setStyle("-fx-stroke: BLACK;");
				for(int i = 0; i < Main.project.getTimeline(id).getEvents().size(); i++) {
					for(int j = 0; j<FinalCounter; j++){
						if(Main.project.getTimeline(id).getEvents().get(i).getStartTime().getYear() == (startDate.getYear()+FinalCounter*counter+j)) {
							timeLine.setStyle("-fx-stroke: "+Main.project.getTimeline(id).getEvents().get(i).getColorName()+";");
						}	
					}
				}
			}
        });
		

		//On mouse click the timeline zooms in.
  		timeLine.setOnMouseClicked(e ->{
  			Stage stage2 = new Stage();
  			stage2.setOnCloseRequest(event -> {
				   stage2.setScene(null);
				});
  			
  			ScrollPane zoomPane = new ScrollPane();
  			VBox zoomBox = new VBox();
  			StartingModeController.timelineIdToModify=id;
  			//If years is smaller than five years.
			if(FinalCounter>5){
				if(counter == 0){
					zoomBox.getChildren().addAll(yearShow(id,startDate,startDate.plusYears(FinalCounter)),generateTimeL(id, startDate, startDate.plusYears(FinalCounter)),spaceBetween());
					zoomPane.setContent(zoomBox);
				
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==1){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter),startDate.plusYears(FinalCounter*2)),generateTimeL(id, startDate.plusYears(FinalCounter), startDate.plusYears((FinalCounter*2))),spaceBetween());
					zoomPane.setContent(zoomBox); 
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==2){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter*2),startDate.plusYears(FinalCounter*3)),generateTimeL(id, startDate.plusYears(FinalCounter*2), startDate.plusYears((FinalCounter*3))),spaceBetween());
					zoomPane.setContent(zoomBox);
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==3){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter*3),startDate.plusYears(FinalCounter*4)),generateTimeL(id, startDate.plusYears(FinalCounter*3), startDate.plusYears((FinalCounter*4))),spaceBetween());
					zoomPane.setContent(zoomBox);
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==4){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter*4),startDate.plusYears(FinalCounter*5)),generateTimeL(id, startDate.plusYears(FinalCounter*4), startDate.plusYears((FinalCounter*5))),spaceBetween());
					zoomPane.setContent(zoomBox);
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}	
		}
			else {
				if((int)yearCounter(startDate,endDate)<2){
					showInfoByMonth (0, startDate.getYear(), startDate.getMonthValue(), timeLine);
				}
				else if(FinalCounter==0){
					zoomBox.getChildren().add(zoomedTimeline(id, startDate.plusYears(counter), endDate,startDate,counter));
					zoomPane.setContent(zoomBox);
					stage2.setTitle("Zoomed timeline");
					stage2.show();
				}
				else{
				zoomBox.getChildren().add(zoomedTimeline(id, startDate.plusYears(FinalCounter*counter), endDate,startDate,counter));
				zoomPane.setContent(zoomBox); 
				stage2.setTitle("Zoomed timeline");
				stage2.show();
			}
			}
		
		stage2.setScene(new Scene(zoomPane));
		
		});
  		
  		
		return timeLine;
	}
	
	public Line Hline(int size){		//Making non clickable horizontal line
		Line timeLine = new Line(0, 50, size, 50);
		
		return timeLine;
	}
	
	public Pane generateTimeL(int id, LocalDate StartDate, LocalDate EndDate){	//Method that is making timelines with horizontal and vertical lines
		Text title = new Text();
		title.setText(Main.project.getTimeline(id).getTitle() + "\n" + Main.project.getTimeline(id).getStartDate() + "\n" + Main.project.getTimeline(id).getEndDate());
		title.setFont(Font.font ("Verdana", 15));
		
		int size = (int) title.getBoundsInLocal().getWidth();	//Making a gap before every timeline so the name will show right
		
		//Name length represent by rectangle.
		Rectangle rectangle = new Rectangle(size + 50, 20);
		rectangle.setFill(Color.TRANSPARENT);
			
		Pane pane = new Pane();
		HBox hbox = new HBox();
		hbox.getChildren().add(rectangle);
		
		int start = StartDate.getYear();
		int end = EndDate.getYear();
		int temp = end - start;
		
		if(temp > 5 || temp == 5) {
			for(int i = 0; i < 5; i++) {
				hbox.getChildren().addAll(verticalLine(100),clickAbleHline(242,id,i,StartDate,EndDate));  //generating timeline
			}
		}
		else {
			switch(temp) {
			case 0: {
				int startMonth = StartDate.getMonthValue();
				int endMonth = EndDate.getMonthValue();
				int tempMonth = endMonth - startMonth;
				
				if(tempMonth == 0) {
					hbox.getChildren().addAll(verticalLine(100),clickAbleHline(250,id,1,StartDate,EndDate));	
				}
				
				for(int i = 0; i < tempMonth; i++) {
					hbox.getChildren().addAll(verticalLine(100),clickAbleHline(250,id,i,StartDate,EndDate));		
				}
				break;
			
			}
		 			case 1: {
		 				for(int i = 0; i < 1; i++) {
		 					hbox.getChildren().addAll(verticalLine(100),clickAbleHline(1250,id,i,StartDate,EndDate));  //generating timeline
		 				}
		 				break;
		 			}
		 			case 2: {
		 				for(int i = 0; i < 2; i++) {
		 					hbox.getChildren().addAll(verticalLine(100),clickAbleHline(625,id,i,StartDate,EndDate));  //generating timeline
		 				}
		 				break;
		 			}
		 			case 3: {
		 				for(int i = 0; i < 3; i++) {
		 				hbox.getChildren().addAll(verticalLine(100),clickAbleHline(417,id,i,StartDate,EndDate));  //generating timeline
						}
		 				break;
		 			}
		 			case 4: {
		 				for(int i = 0; i < 4; i++) {
		 					hbox.getChildren().addAll(verticalLine(100),clickAbleHline(310,id,i,StartDate,EndDate));  //generating timeline
		 				}
		 				break;
		 			}
		 			}
		  		}
		
		hbox.getChildren().addAll(verticalLine(100));
		hbox.setLayoutX(5);
		hbox.setLayoutY(0);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(hbox);
		
		return pane;
	}
	
	/**
	 * Horizontal line for the zoomed in timeline.
	 * @param size of the line.
	 * @param id of timeline.
	 * @param startDate of timeline.
	 * @return horizontal line.
	 */
	public Line zommedHline(int size, int id ,LocalDate startDate){		//Making non clickable horizontal line
		
		Line timeLine = new Line(0, 50, size, 50);
		
		timeLine.setOnMouseClicked(e -> {
			showInfoByMonth (0, startDate.getYear(), startDate.getMonthValue(), timeLine);
			
		});
			primaryScrollpane.setContent(scrollBox);
			Stage stage = (Stage) doneButton.getScene().getWindow();
			stage.close();
			
	

		return timeLine;
	}
	
	/**
	 * Generates a zoomed in timeline.
	 * @param id of timeline.
	 * @param startyear 
	 * @param EndDate of time span.
	 * @param startDate of time span.
	 * @param counter
	 * @return a timeline.
	 */
	public Pane zoomedTimeline(int id, LocalDate startyear, LocalDate EndDate, LocalDate startDate, int counter) {			//This will be the final timeline that you click on to get to the calendar
		
		Pane pane = new Pane();
		HBox hbox = new HBox();
		HBox yearShowHBox = new HBox();
		
		
		int Ycounter;
		int years;
		int temp;
		
		
		Text text = new Text();
		text.setText(Main.project.getTimeline(id).getTitle()+ "\n" + Main.project.getTimeline(id).getStartDate() + "\n" + Main.project.getTimeline(id).getEndDate());
		text.setFont(Font.font ("Verdana", 15));
		int sixe = (int)text.getBoundsInLocal().getWidth();
		
		Rectangle rectangle = new Rectangle(sixe + 10, 20);
		rectangle.setFill(Color.TRANSPARENT);
		hbox.getChildren().add(rectangle);
		
		
		if(counter==4){
			if(yearCounter(startDate,EndDate)%5 == 0){
				Ycounter = (int)(yearCounter(startDate,EndDate)/5);
			}
			else{
				years = (int)((yearCounter(startDate,EndDate)-(yearCounter(startDate,EndDate)%5)));
				Ycounter = years - years%4;
				Ycounter = Ycounter/4;
			}
			temp = EndDate.getYear()-startDate.plusYears(Ycounter*4).getYear();
	
		}else{
		if(yearCounter(startDate,EndDate)%5 == 0){
			Ycounter = (int)(yearCounter(startDate,EndDate)/5);
		}
		else{
			years = (int)((yearCounter(startDate,EndDate)-(yearCounter(startDate,EndDate)%5)));
			Ycounter = years - years%4;
			Ycounter = Ycounter/4;
		}
		temp = Ycounter;
		}
		
		if(temp < 5) {
			switch(temp) {
			case 0: {
				hbox.getChildren().addAll(verticalLine(100),Hline(92));
				yearShowHBox.getChildren().add(yearShow(id, startyear, startyear.plusYears(1)));
				for(int j = 0; j < 12; j++) {	
					hbox.getChildren().addAll(KlickverticalLine(50,startyear.plusMonths(-startDate.getMonthValue()+1+j), id),Hline(92));
				}
				
				break;
			}
			
			case 1: {
				hbox.getChildren().addAll(verticalLine(100),Hline(92));
				yearShowHBox.getChildren().add(yearShow(id, startyear, startyear.plusYears(1)));
				for(int j = 0; j < 12; j++) {	
					hbox.getChildren().addAll(KlickverticalLine(50,startyear.plusMonths(-startDate.getMonthValue()+1+j), id),Hline(92));
				}
				
				break;
			}
			case 2: {
				yearShowHBox.getChildren().add(yearShow(id, startyear, startyear.plusYears(2)));
				
				for(int i = 0; i < 2; i++) {
					hbox.getChildren().addAll(verticalLine(100),Hline(44));
					for(int j = 0; j < 12; j++) {	
						hbox.getChildren().addAll(KlickverticalLine(50,startyear.plusMonths(-startDate.getMonthValue()+1+j).plusYears(i), id) ,Hline(44));
					}
				}
				
				
				break;
			}
			case 3: {
				
				yearShowHBox.getChildren().add(yearShow(id, startyear, startyear.plusYears(3)));
				for(int i = 0; i < 3; i++) {
					hbox.getChildren().addAll(verticalLine(100),Hline(27));
					for(int j = 0; j < 12; j++) {	
						hbox.getChildren().addAll(KlickverticalLine(50,startyear.plusMonths(-startDate.getMonthValue()+1+j).plusYears(i), id),Hline(29));
					}
				}
				break;
			}
			case 4: {
				
				yearShowHBox.getChildren().add(yearShow(id, startyear, startyear.plusYears(4)));
				for(int i = 0; i < 4; i++) {
					hbox.getChildren().addAll(verticalLine(100),Hline(22));
					for(int j = 0; j < 12; j++) {	
						hbox.getChildren().addAll(KlickverticalLine(50,startyear.plusMonths(-startDate.getMonthValue()+1+j).plusYears(i), id),Hline(20));
					}
				}
				break;
			}
			}
		}
		else {
			yearShowHBox.getChildren().add(yearShow(id, startyear, startyear.plusYears(5)));
			for(int j = 0; j<5; j++){
				hbox.getChildren().addAll(verticalLine(100),Hline(15));
			for(int i = 0; i < 12; i++) {	
				hbox.getChildren().addAll(KlickverticalLine(50,startyear.plusMonths(-startDate.getMonthValue()+1+i).plusYears(j), id),Hline(15));
			}
			}
		}
		
		
		hbox.getChildren().addAll(Hline(8), verticalLine(100));
		hbox.setLayoutX(50);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(yearShowHBox, hbox);
		

		
		return pane;	
	}
	
	public double yearCounter(LocalDate StartDate, LocalDate EndDate) {	//Counting the years
		  return Math.round(EndDate.getYear() - StartDate.getYear());
		 }
	
	public int monthCounter(LocalDate StartDate, LocalDate EndDate) {	//Counting months
	  
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
	
	public Pane yearShow(int id,LocalDate startDate, LocalDate endDate) {			//Method that is displaying the name and years over the timeline
		  
		  Pane pane = new Pane();
		  
		  HBox yearBox = new HBox();
		  
		  Rectangle rectangle = new Rectangle(45, 20);
		  rectangle.setFill(Color.TRANSPARENT);
		  
		  Text title = new Text();
		  title.setText(Main.project.getTimeline(id).getTitle() + "\n" + Main.project.getTimeline(id).getStartDate() + "\n" + Main.project.getTimeline(id).getEndDate());
		  title.setFont(Font.font ("Verdana", 15));
		  title.setOnMouseClicked(event -> { modifyTimeline(id);});
		  yearBox.getChildren().addAll(title,rectangle);  
		  
		  int yearsTemp = (int)yearCounter(startDate, endDate);
		  
		  
		  if(yearsTemp > 5 || yearsTemp == 5) {
			  if(yearCounter(startDate,endDate)>=5){
				  	if(yearCounter(startDate,endDate)%5 == 0){
				  		for(int i = 0; i <= (yearCounter(startDate,endDate)); i+=Math.round((yearCounter(startDate,endDate)/5))) {
				  		Rectangle rec = new Rectangle(222, 1);
				  		rec.setFill(Color.TRANSPARENT);
				  		String temp =String.valueOf(startDate.getYear()+i); //- startDate.getYear()));
				  		Text text = new Text();
				  		text.setText(temp);	  
				  		yearBox.getChildren().addAll(text,rec);
				  		}
			  
				  	}
				  	
				  	if(yearCounter(startDate,endDate)%5 > 0){
				  		int years = (int)((yearCounter(startDate,endDate)-(yearCounter(startDate,endDate)%5)));
				  		int counter = years - years%4;
				  			for (int i=0; i<=counter; i+=counter/4){
				  				Rectangle rec = new Rectangle(222, 1);
				  				rec.setFill(Color.TRANSPARENT);
				  				String temp =String.valueOf(startDate.getYear()+i); //- startDate.getYear()));
				  				Text text = new Text();
				  				text.setText(temp);	  
				  				yearBox.getChildren().addAll(text,rec);
					  			}
				  			String temp =String.valueOf(endDate.getYear());
				  			Text text = new Text();
				  			text.setText(temp);
			  
				  			yearBox.getChildren().add(text);
				  			
				  	}
			  }
			  
		  }
		  else {
			  switch(yearsTemp) {
			  case 0: {
				  int startMonth = startDate.getMonthValue();
				  int endMonth = endDate.getMonthValue();
				  int tempMonth = endMonth - startMonth;
				 
				  for(int i = 0; i <= tempMonth; i++) {
					  
					  Rectangle rec = new Rectangle(220, 1);
					  rec.setFill(Color.TRANSPARENT);
					  Text text = new Text();
					  String tempText = startDate.plusMonths(i).getMonth().toString();
					  text.setText(tempText);
					  yearBox.getChildren().addAll(text, rec);
					}
				  
				  break;
			  }
			  
			  case 1: {
				  for(int i = 0; i < yearsTemp; i++) {
					  	Rectangle rec = new Rectangle(1230, 1);
		  				rec.setFill(Color.TRANSPARENT);
		  				String temp =String.valueOf(startDate.getYear() + i); //- startDate.getYear()));
		  				Text text = new Text();
		  				text.setText(temp);	  
		  				yearBox.getChildren().addAll(text,rec);
				  }
				  Text text = new Text();
				  text.setText(String.valueOf(endDate.getYear()));
				  yearBox.getChildren().add(text);
				  
				  break;
			  }
			  case 2: {
				  for(int i = 0; i < yearsTemp; i++) {
					  	Rectangle rec = new Rectangle(600, 1);
		  				rec.setFill(Color.TRANSPARENT);
		  				String temp =String.valueOf(startDate.getYear() + i); //- startDate.getYear()));
		  				Text text = new Text();
		  				text.setText(temp);	  
		  				yearBox.getChildren().addAll(text,rec);
				  }
				  Text text = new Text();
				  text.setText(String.valueOf(endDate.getYear()));
				  yearBox.getChildren().add(text);
				  
				  break;
			  }
			  case 3: {
				  for(int i = 0; i < yearsTemp; i++) {
					  	Rectangle rec = new Rectangle(400, 1);
		  				rec.setFill(Color.TRANSPARENT);
		  				String temp =String.valueOf(startDate.getYear() + i); //- startDate.getYear()));
		  				Text text = new Text();
		  				text.setText(temp);	  
		  				yearBox.getChildren().addAll(text,rec);
				  }
				  Text text = new Text();
				  text.setText(String.valueOf(endDate.getYear()));
				  yearBox.getChildren().add(text);
				  
				  break;
			  }
			  case 4: {
				  for(int i = 0; i < yearsTemp; i++) {
					  	Rectangle rec = new Rectangle(290, 1);
		  				rec.setFill(Color.TRANSPARENT);
		  				String temp =String.valueOf(startDate.getYear() + i); //- startDate.getYear()));
		  				Text text = new Text();
		  				text.setText(temp);	  
		  				yearBox.getChildren().addAll(text,rec);
				  }
				  Text text = new Text();
				  text.setText(String.valueOf(endDate.getYear()));
				  yearBox.getChildren().add(text);
			  }
			  break;
			  }
		  }
		  		
		pane.getChildren().add(yearBox);
		  
		return pane;
			 		 		
	}
	
	public Pane spaceBetween(){
	      Pane pane = new Pane();
		  
		  HBox blank = new HBox();
		  
		  Rectangle rectangle = new Rectangle(45, 80);
		  rectangle.setFill(Color.TRANSPARENT);
		  
		  blank.getChildren().add(rectangle);
		  
		  pane.getChildren().add(blank);
		  
		  return pane;		
	}
	
	public void modifyTimeline(int id){
		  StartingModeController.timelineIdToModify=id;
		  FXMLLoader loader = new FXMLLoader();
		  loader.setLocation(Main.class.getResource("CreateMode.fxml"));
		  FlowPane createMode= new FlowPane();
		  try {
			  createMode = loader.load();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  CreateModeController c = (CreateModeController) loader.getController();
		  Timeline temp = Main.project.getTimeline(id);
		  c.TimelineName.setText(temp.getTitle()); 
		  c.StartDate.setValue(temp.getStartDate());
		  c.EndDate.setValue(temp.getEndDate());
		  c.CreateButton.setText("Save");
		  c.CreateButton.setTranslateX(170);
		  c.deleteButton.setVisible(true);
		  c.TimelineID=id;
		  Stage stage = new Stage();
		  stage.setScene(new Scene(createMode));  
		  stage.setTitle("Edit Timeline");
		  stage.showAndWait();
		  refreshTimeline();
	}
	
	
	/**
	 * 
	 * @param timelineId     the id of which TimeLine you clicked.
	 * @param year           which year you clicked 
	 * @param month			which month you clicked.
	 */
	public void showInfoByMonth (final int timelineId, int year, int month, Line timeLine) {

		final DatePicker startDatePicker = new DatePicker();
		startDatePicker.setValue(LocalDate.of(year, month, 1));
		
		this.timelineId = timelineId;

		/**
		 * set the style relation about the cellEdit of the DaterPicker
		 */
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						int eventCount=0;
						String c= Color.WHITE.toString();
						super.updateItem(item, empty);
						List<Event> events = Main.project.getTimeline(StartingModeController.timelineIdToModify).getEvents();
						for (Event e : events) {	
							if(e.getStartTime().toLocalDate().equals(item)){
								eventCount++;
								c = e.getColorName();
							}
								
						}
						if(item.isBefore(Main.project.getTimeline(StartingModeController.timelineIdToModify).getStartDate()) || item.isAfter(Main.project.getTimeline(StartingModeController.timelineIdToModify).getEndDate())){
							this.setDisable(true);
						}
						
						this.setOnMouseClicked(new EventHandler<MouseEvent>() {		//add a action event to the DaterPicker.
							@Override
							public void handle(MouseEvent event) {

								List<Event> events = new ArrayList<>(Main.project.getTimeline(StartingModeController.timelineIdToModify).getEvents());
								for (int i=0; i<events.size(); i++) {	
									if(!events.get(i).getStartTime().toLocalDate().equals(startDatePicker.getValue()))
										events.remove(i--);
								}
								
								try {
									if (events.isEmpty()){
										StartingModeController.eventIdToModify=0;
										FXMLLoader loader = new FXMLLoader();
										loader.setLocation(Main.class.getResource("eventHandler.fxml"));
										Pane showEventHandler = loader.load();
										eventHandlerController handler = loader.getController();
										handler.startTextField.setValue(startDatePicker.getValue());
										Stage stage2 = new Stage();
										stage2.setScene(new Scene(showEventHandler));  
										stage2.setTitle("Add new event");
										stage2.showAndWait();
										startDatePicker.setValue(handler.getStartValue().getValue().plusDays(1));
										startDatePicker.setValue(handler.getStartValue().getValue());

										
									}
									else{
										events.add(new NonDurationEvent("New event",LocalDateTime.of(startDatePicker.getValue(), LocalTime.of(0, 0)),"",null,Color.RED, null));
										events.get(events.size()-1).setEventId(0);
										StartingModeController.eventIdToModify=events.get(0).getEventId();
										FXMLLoader loader = new FXMLLoader();
										loader.setLocation(Main.class.getResource("eventHandler.fxml"));
										Pane showEventHandler = loader.load();
										eventHandlerController handler = loader.getController();
										ListView<Event> eventListView = new ListView<>();
										eventListView.setPrefWidth(150);
										eventListView.setItems(FXCollections.observableArrayList(events));
										eventListView.getSelectionModel().selectedItemProperty().addListener( ov -> {
											StartingModeController.eventIdToModify=eventListView.getSelectionModel().getSelectedItem().getEventId();
											handler.initialize();
											});
										
										HBox layout = new HBox();
										layout.getChildren().addAll(eventListView,showEventHandler);
										Stage stage2 = new Stage();
										stage2.setScene(new Scene(layout));  
										stage2.setTitle("Manage and edit events");
										stage2.showAndWait();
										startDatePicker.setValue(handler.getStartValue().getValue().plusDays(1));
										startDatePicker.setValue(handler.getStartValue().getValue());
										
										
									}
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						});
						
						if (eventCount>0){
							// if (eventCount>1) c="fuchsia";
							this.setText(this.getText()+" ("+ eventCount+")");			
							setStyle("-fx-background-color: " + c);			//set the color of the cell
							setTooltip(new Tooltip(eventCount+" Event/s"));
						}
					}	
				};
			}
		};
		
		startDatePicker.autosize();
		startDatePicker.setMinWidth(450);
		startDatePicker.setDayCellFactory(dayCellFactory);
		DatePickerSkin datePickerSkin = new DatePickerSkin(startDatePicker);
		Node popupContent = datePickerSkin.getPopupContent();
		
		popupContent.applyCss();

		BorderPane borderPane = new BorderPane();
		borderPane.setMinWidth(startDatePicker.getMinWidth());
		borderPane.setCenter(popupContent);
		
		Stage stage2 = new Stage();
		stage2.setScene(new Scene(borderPane)); 
		stage2.setTitle("Calendar");
		stage2.sizeToScene();
		stage2.show();
	}

}