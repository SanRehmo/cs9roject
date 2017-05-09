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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import cs_9roject.Event;
import cs_9roject.NonDurationEvent;
import cs_9roject.Timeline;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Callback;


public class showTimelinesController {
	public int timelineId = 0;
	
	public ScrollPane primaryScrollpane;
	
	public BorderPane primaryBorderpane; 

	List<CheckBox> timelines = new ArrayList<CheckBox>();


	VBox vbox = new VBox();
	
	VBox scrollBox = new VBox();
	
	ScrollPane zoomPane = new ScrollPane();
	VBox zoomBox = new VBox();
	
	
	 @FXML
	 public void initialize() {  //Reading every timeline and print there names in checkboxes
		 for(int i=0; i<Main.project.getTimelines().size(); i++){
			 HBox hbox = new HBox();
			 Pane pane = new Pane();
			 System.out.println(Main.project.getTimelines().size());
			 CheckBox cbi = new CheckBox( Main.project.getTimelines().get(i).getTitle() +" (" + Main.project.getTimelines().get(i).getEvents().size() +" event/s) ID: " + Main.project.getTimelines().get(i).getTimelineId() );
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
	private ScrollPane show_scrollpane; //ScrollPane from starting window

	@FXML
	private Button doneButton;

	@FXML
	private CheckBox displayAll;
	
	@FXML
	public void showTimeline(){	//Method that is showing the timelines in the scrollPane
		if(displayAll.isSelected()){//If DisplayAll is selected the program will show every timeline
			for(int i=0; i<timelines.size(); i++){
			scrollBox.getChildren().addAll(yearShow(Main.project.getTimelines().get(i).getTimelineId(),Main.project.getTimelines().get(i).getStartDate(),Main.project.getTimelines().get(i).getEndDate()),generateTimeL(Main.project.getTimelines().get(i).getTimelineId(), Main.project.getTimelines().get(i).getStartDate(), Main.project.getTimelines().get(i).getEndDate()),spaceBetween());
			}
			
		}
		else{
			for(int i=0; i<timelines.size(); i++){	//Just displaying the checked timelines
				if(timelines.get(i).isSelected()){
				scrollBox.getChildren().addAll(yearShow(Main.project.getTimelines().get(i).getTimelineId(),Main.project.getTimelines().get(i).getStartDate(),Main.project.getTimelines().get(i).getEndDate()),generateTimeL(Main.project.getTimelines().get(i).getTimelineId(), Main.project.getTimelines().get(i).getStartDate(), Main.project.getTimelines().get(i).getEndDate()),spaceBetween());
				}	
			}	 
		}
	 primaryScrollpane.setContent(scrollBox);
	 Stage stage = (Stage) doneButton.getScene().getWindow();
	 
	 stage.close();	
	}
	
	public Line verticalLine(int size) {		//Making every vertical lines in a timeline
		Line timeLine = new Line(50,0,50,size);
		timeLine.setStrokeWidth(3);
		return timeLine;
	}
	
public Line clickAbleHline(int size, int id, int counter, LocalDate startDate, LocalDate endDate) {	//Making a horizontal line that will open eventhandler when you press the line
				
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
		
		int FinalCounter = Ycounter;
		
  		timeLine.setOnMouseClicked(e ->{
  			StartingModeController.timelineIdToModify=id;
			if(FinalCounter>5){
				if(counter == 0){
					zoomBox.getChildren().addAll(yearShow(id,startDate,startDate.plusYears(FinalCounter)),generateTimeL(id, startDate, startDate.plusYears(FinalCounter)),spaceBetween());
					zoomPane.setContent(zoomBox);
			  		Stage stage2 = new Stage();
					stage2.setScene(new Scene(zoomPane));  
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==1){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter),startDate.plusYears(FinalCounter*2)),generateTimeL(id, startDate.plusYears(FinalCounter), startDate.plusYears((FinalCounter*2))),spaceBetween());
					zoomPane.setContent(zoomBox);
			  		Stage stage2 = new Stage();
					stage2.setScene(new Scene(zoomPane));  
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==2){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter*2),startDate.plusYears(FinalCounter*3)),generateTimeL(id, startDate.plusYears(FinalCounter*2), startDate.plusYears((FinalCounter*3))),spaceBetween());
					zoomPane.setContent(zoomBox);
			  		Stage stage2 = new Stage();
					stage2.setScene(new Scene(zoomPane));  
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==3){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter*3),startDate.plusYears(FinalCounter*4)),generateTimeL(id, startDate.plusYears(FinalCounter*3), startDate.plusYears((FinalCounter*4))),spaceBetween());
					zoomPane.setContent(zoomBox);
			  		Stage stage2 = new Stage();
					stage2.setScene(new Scene(zoomPane));  
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}
				else if(counter ==4){
					zoomBox.getChildren().addAll(yearShow(id,startDate.plusYears(FinalCounter*4),startDate.plusYears(FinalCounter*5)),generateTimeL(id, startDate.plusYears(FinalCounter*4), startDate.plusYears((FinalCounter*5))),spaceBetween());
					zoomPane.setContent(zoomBox);
			  		Stage stage2 = new Stage();
					stage2.setScene(new Scene(zoomPane));  
					stage2.setTitle("Zoomed timeline");
					stage2.show();
					}	
		}
			else {
				zoomBox.getChildren().add(zoomedTimeline(id, startDate));
				zoomPane.setContent(zoomBox);
		  		Stage stage2 = new Stage();
				stage2.setScene(new Scene(zoomPane));  
				stage2.setTitle("Zoomed timeline");
				stage2.show();
			} 
		
		
			/*zoomPane.setContent(zoomBox);
	  		Stage stage2 = new Stage();
			stage2.setScene(new Scene(zoomPane));  
			stage2.setTitle("Zoomed timeline");
			stage2.show();
				*/	
		});
  		
		return timeLine;
	}
	
	public Line Hline(int size){		//Making non clickable horizontal line
		Line timeLine = new Line(0, 50, size, 50);
		
		return timeLine;
	}
	
	public Pane generateTimeL(int id, LocalDate StartDate, LocalDate EndDate){	//Method that is making timelines with horizontal and vertical lines
		Text title = new Text();
		title.setText(Main.project.getTimeline(id).getTitle());
		title.setFont(Font.font ("Verdana", 20));
		
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
				hbox.getChildren().addAll(verticalLine(100),clickAbleHline(250,id,i,StartDate,EndDate));  //generating timeline
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
		 					hbox.getChildren().addAll(verticalLine(100),clickAbleHline(312,id,i,StartDate,EndDate));  //generating timeline
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
	
	public Line zommedHline(int size, int id ,LocalDate startDate){		//Making non clickable horizontal line
		
		Line timeLine = new Line(0, 50, size, 50);
		
			/*timeLine.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					showInfoByMonth(id, 2017, 5);
				}
			});*/
		
		timeLine.setOnMouseClicked(e -> {
			showInfoByMonth (0, 2017, 4);
			
		});
			primaryScrollpane.setContent(scrollBox);
			Stage stage = (Stage) doneButton.getScene().getWindow();
			stage.close();
			
	

		return timeLine;
	}
	
	
	public Pane zoomedTimeline(int id, LocalDate startDate) {			//This will be the final timeline that you click on to get to the calendar
		Pane pane = new Pane();
		HBox hbox = new HBox();

		for(int j = 0; j<5; j++){
			hbox.getChildren().addAll(verticalLine(100),Hline(12));
		for(int i = 0; i < 12; i++) {	
			hbox.getChildren().addAll(verticalLine(50),zommedHline(12,id,startDate));
		}
		}
		hbox.getChildren().addAll(Hline(8), verticalLine(100));
		hbox.setLayoutX(5);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().add(hbox);
		
		pane.setOnMouseClicked(e-> {
			showInfoByMonth (0, 2017, 4);
		});
		
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
		  title.setText(Main.project.getTimeline(id).getTitle());
		  title.setFont(Font.font ("Verdana", 20));
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
	
	/**
	 * 
	 * @param timelineId     the id of which TimeLine you clicked.
	 * @param year           which year you clicked 
	 * @param month			which month you cliced.
	 */
	public void showInfoByMonth (final int timelineId, int year, int month) {
		FXMLLoader loader = new FXMLLoader();
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
						super.updateItem(item, empty);
						List<Event> events = Main.project.getTimeline(StartingModeController.timelineIdToModify).getEvents();
						for (Event e : events) {	
							if(e.getStartTime().toLocalDate().equals(item))
								eventCount++;
						}
						if (eventCount>0){
							
							this.setText(this.getText()+" "+ eventCount);
							String color = events.get(eventCount).getColor().toString().substring(4);
							
							
							setStyle("-fx-background-color: #" + color);			//set the color of the cell
							setTooltip(new Tooltip(eventCount+" Event/s"));
						}
	
					}	
				};
			}
		};
		
		startDatePicker.setOnAction(new EventHandler<ActionEvent>() {		//add a action event to the DaterPicker.
			public void handle(ActionEvent e) {
				List<Event> events = new ArrayList<>(Main.project.getTimeline(StartingModeController.timelineIdToModify).getEvents());
				System.out.println("alaa "+events.size());
				for (int i=0; i<events.size(); i++) {	
					if(!events.get(i).getStartTime().toLocalDate().equals(startDatePicker.getValue()))
						events.remove(i--);
				}
				System.out.println("alaa "+events.size());
				
				try {
					if (events.isEmpty()){
						StartingModeController.eventIdToModify=0;
						Main.showEventHandler();
					}
					else{
						events.add(new NonDurationEvent("New event",null,"",null,Color.RED));
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
						stage2.setTitle("EventHandler");
						stage2.show();
					}
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		startDatePicker.setDayCellFactory(dayCellFactory);
		@SuppressWarnings("restriction")
		DatePickerSkin datePickerSkin = new DatePickerSkin(startDatePicker);
		@SuppressWarnings("restriction")
		Node popupContent = datePickerSkin.getPopupContent();
		popupContent.applyCss();
		popupContent.autosize();
	
		
		
		
		//primaryBorderpane.getChildren().add(popupContent);
		//primaryBorderpane.setAlignment(popupContent, Pos.BOTTOM_LEFT);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(popupContent);
		
		Stage stage2 = new Stage();
		stage2.setScene(new Scene(borderPane)); 
		stage2.setTitle("Calendar");
		stage2.sizeToScene();
		stage2.show();
	}
}