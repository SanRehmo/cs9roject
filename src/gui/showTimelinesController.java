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

import cs_9roject.Timeline;
import javafx.scene.paint.Color;






import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Callback;




public class showTimelinesController {
	public int timelineId = 0;
	
	public ScrollPane primaryScrollpane;

	List<CheckBox> timelines = new ArrayList<CheckBox>();


	VBox vbox = new VBox();
	
	VBox scrollBox = new VBox();
	
	 @FXML
	 public void initialize() {  //Reading every timeline and print there names in checkboxes
		 for(int i=0; i<Main.project.getTimelines().size(); i++){
			 HBox hbox = new HBox();
			 Pane pane = new Pane();
			 System.out.println(Main.project.getTimelines().size());
			 CheckBox cbi = new CheckBox( Main.project.getTimelines().get(i).getTitle() +" ( " + Main.project.getTimelines().get(i).getEvents().size() +" event(s) )" );
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
			scrollBox.getChildren().addAll(yearShow(Main.project.getTimelines().get(i).getTimelineId()),generateTimeL(Main.project.getTimelines().get(i).getTimelineId()),spaceBetween());
			}
			
		}
		else{
			for(int i=0; i<timelines.size(); i++){	//Just displaying the checked timelines
				if(timelines.get(i).isSelected()){
				scrollBox.getChildren().addAll(yearShow(Main.project.getTimelines().get(i).getTimelineId()),generateTimeL(Main.project.getTimelines().get(i).getTimelineId()),spaceBetween());
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
	
	public Line clickAbleHline(int size) {	//Making a horizontal line that will open eventhandler when you press the line
		
		Line timeLine = new Line(0, 50, size, 50);
		timeLine.setStrokeWidth(5);
		
		timeLine.setOnMouseClicked(e ->{
			this.scrollBox.getChildren().add(zoomedTimeline());
		});
		
		
		
		
		return timeLine;
	}
	
	public Line Hline(int size){		//Making non clickable horizontal line
		Line timeLine = new Line(0, 50, size, 50);
		
		return timeLine;
	}
	
	public Pane generateTimeL(int id){	//Method that is making timelines with horizontal and vertical lines
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
		
		for(int i = 0; i < 5; i++) {
			hbox.getChildren().addAll(verticalLine(100),clickAbleHline(250));  //generating timeline
		}
		
		hbox.getChildren().addAll(verticalLine(100));
		hbox.setLayoutX(5);
		hbox.setLayoutY(0);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(hbox);
		
		return pane;
	}
	
	public Line zommedHline(int size){		//Making non clickable horizontal line
		
		Line timeLine = new Line(0, 50, size, 50);
		
		
			scrollBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent e) {
					showInfoByMonth(0, 2017, 5);
				}
			});
			primaryScrollpane.setContent(scrollBox);
			Stage stage = (Stage) doneButton.getScene().getWindow();
			stage.close();
			
	

		return timeLine;
	}
	
	
	public Pane zoomedTimeline() {			//This will be the final timeline that you click on to get to the calendar
		Pane pane = new Pane();
		HBox hbox = new HBox();
		for(int j = 0; j<5; j++){
			hbox.getChildren().addAll(verticalLine(100),Hline(12));
		for(int i = 0; i < 12; i++) {	
			hbox.getChildren().addAll(verticalLine(50),zommedHline(12));
		}
		}
		hbox.getChildren().addAll(Hline(10), verticalLine(12));
		hbox.setLayoutX(5);
		hbox.setLayoutY(30);
		hbox.setAlignment(Pos.CENTER);
		pane.getChildren().add(hbox);
		
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
	
	public Pane yearShow(int id) {			//Method that is displaying the name and years over the timeline
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
		  
		  if(yearCounter(startDate,endDate)>5){
		  for(int i = 0; i <= (yearCounter(startDate,endDate)-Math.floor(yearCounter(startDate,endDate)/5)); i+=Math.round((yearCounter(startDate,endDate)/5))) {
		  Rectangle rec = new Rectangle(222, 1);
		  rec.setFill(Color.TRANSPARENT);
	      String temp =String.valueOf(startDate.getYear()+i); //- startDate.getYear()));
		  Text text = new Text();
		  text.setText(temp);	  
		  yearBox.getChildren().addAll(text,rec);
		  }
		  }
		  String temp =String.valueOf(endDate.getYear());
		  Text text = new Text();
		  text.setText(temp);
		  
		  yearBox.getChildren().add(text);
		  
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
						super.updateItem(item, empty);
						List<Timeline> timelines = Main.project.getTimelines();

						for (Timeline t : timelines) {	
							if (t.getTimelineId() == timelineId) {		//get the infomation of the Timeline you clicked
								List<cs_9roject.Event> events = t.getEvents();
								for (cs_9roject.Event e : events) {		//get all the events of the the timeline
									LocalDateTime startTime = e.getStartTime();	
									LocalDateTime endTime = e.getEndTime();
									
									String startMonth = startTime.getMonthValue() / 10 > 0 ? "" + startTime.getMonthValue() : "0" + startTime.getMonthValue();
									String startDay = startTime.getDayOfMonth() / 10 > 0 ? "" + startTime.getDayOfMonth() : "0" + startTime.getDayOfMonth();
									
									String startTimeStr = startTime.getYear() + "-" + startMonth + "-" + startDay;
									String endTimeStr = "";
									
									if (endTime == null) {		// if the end time is not duration
										endTimeStr = startTimeStr;
									} else {
										String endMonth = endTime.getMonthValue() / 10 > 0 ? "" + endTime.getMonthValue() : "0" + endTime.getMonthValue();
										String endDay = endTime.getDayOfMonth() / 10 > 0 ? "" + endTime.getDayOfMonth() : "0" + endTime.getDayOfMonth();
										endTimeStr = endTime.getYear() + "-" + endMonth + "-" + endDay;
									}
									
									String itemMonth = item.getMonthValue() / 10 > 0 ? "" + item.getMonthValue() : "0" + item.getMonthValue();
									String itemDay = item.getDayOfMonth() / 10 > 0 ? "" + item.getDayOfMonth() : "0" + item.getDayOfMonth();
									String itemStr = item.getYear() + "-" + itemMonth +"-" + itemDay;

									if (itemStr.compareTo(startTimeStr) >= 0 && itemStr.compareTo(endTimeStr) <= 0) {
										Color c = e.getColor();
										String colorStr = c.toString().substring(4);
										setStyle("-fx-background-color: #" + colorStr);			//set the color of the cell
										setTooltip(new Tooltip(e.getTitle()));
									}
								}

								break;
							}
						}
					}	
				};
			}
		};
		
		startDatePicker.setOnAction(new EventHandler<ActionEvent>() {		//add a action event to the DaterPicker.
			public void handle(ActionEvent e) {			
				String handlerTime = startDatePicker.getValue().toString();
				
				boolean isExist = false;
				
				for (Timeline t : Main.project.getTimelines()) {
					if (t.getTimelineId() == timelineId) {				
						List<cs_9roject.Event> events = t.getEvents();
						for (cs_9roject.Event event : events) {
							LocalDateTime startTime = event.getStartTime();
							LocalDateTime endTime = event.getEndTime();
							
							String startMonth = startTime.getMonthValue() / 10 > 0 ? "" + startTime.getMonthValue() : "0" + startTime.getMonthValue();
							String startDay = startTime.getDayOfMonth() / 10 > 0 ? "" + startTime.getDayOfMonth() : "0" + startTime.getDayOfMonth();
							
							String startTimeStr = startTime.getYear() + "-" + startMonth + "-" + startDay;
							String endTimeStr = "";
							
							if (endTime == null) {
								endTimeStr = startTimeStr;
							} else {
								String endMonth = endTime.getMonthValue() / 10 > 0 ? "" + endTime.getMonthValue() : "0" + endTime.getMonthValue();
								String endDay = endTime.getDayOfMonth() / 10 > 0 ? "" + endTime.getDayOfMonth() : "0" + endTime.getDayOfMonth();
								endTimeStr = endTime.getYear() + "-" + endMonth + "-" + endDay;
							}
							
							if (handlerTime.compareTo(startTimeStr) >=0 && handlerTime.compareTo(endTimeStr) <= 0) {
								isExist = true;
								try {
									FXMLLoader loader = new FXMLLoader();
									loader.setLocation(Main.class.getResource("eventHandler.fxml"));		//show the eventHandler GUI by the infomation of the time you clicked
									Pane showEventHandler = loader.load();
									eventHandlerController handler = loader.getController();
									//handler.getStartTextField().setValue(startTime.toLocalDate());
									if (endTime != null)
										handler.getEndValue().setValue(endTime.toLocalDate());
									//handler.getDescription().setText(event.getDescription());
								//	handler.getNameEvent_textField().setText(event.getTitle());
									
									Stage stage2 = new Stage();
									stage2.setScene(new Scene(showEventHandler));  
									stage2.setTitle("EventHandler");
									stage2.show();
								} catch (IOException e1) {
									e1.printStackTrace();
								}		
								break;
							} 
						}
						break;
					}
				}
				
				if (!isExist) {
					try {
						FXMLLoader loader = new FXMLLoader();
						loader.setLocation(Main.class.getResource("eventHandler.fxml"));
						Pane showEventHandler = loader.load();
						eventHandlerController handler = loader.getController();
						
						Stage stage2 = new Stage();
						stage2.setScene(new Scene(showEventHandler));  
						stage2.setTitle("EventHandler");
						stage2.show();
					} catch (IOException e1) {
						e1.printStackTrace();
					}		
				}
			}
		});

		startDatePicker.setDayCellFactory(dayCellFactory);
		DatePickerSkin datePickerSkin = new DatePickerSkin(startDatePicker);
		Node popupContent = datePickerSkin.getPopupContent();
		popupContent.applyCss();
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(popupContent);

		Stage stage2 = new Stage();
		stage2.setScene(new Scene(borderPane, 400, 400)); 
		stage2.setTitle("EventHandler");
		stage2.show();
	}
	
}
