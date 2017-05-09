package gui;


import cs_9roject.DurationEvent;
import cs_9roject.Event;
import cs_9roject.NonDurationEvent;
import cs_9roject.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class eventHandlerController {
	
	@FXML
	private ScrollPane start_scrollpane;
	
	boolean duration  = false;
	boolean recurring = false;
	
	public ObservableList<String> color_Combo = FXCollections.observableArrayList(
			"Red", "Green", "Blue", "Orange");
	
	ObservableList<String> Reccuring_ComboBox_Value = FXCollections.observableArrayList(
			"Every day", "Every week", "Every month", "Every year", "Non");

    @FXML
    private ImageView eventImage_imageView;

    @FXML
    private TextField NameEvent_textField;

    @FXML
    private ComboBox<String> color_ComboBox;

    @FXML
    private Button delete_btn;

    @FXML
    private CheckBox duration_checkBox;

    @FXML
    private Label recurring_label;
    
    @FXML
    private Button btnSave;

    @FXML
    private DatePicker startTextField;
    
    @FXML
    private Spinner<Integer> startHH;
    
    @FXML
    private Spinner<Integer> startMM;

    @FXML
    private DatePicker endTextField;
    
    @FXML
    private Spinner<Integer> endHH;
    
    @FXML
    private Spinner<Integer> endMM;
    
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<String> Reccuring_ComboBox;
    
    public static boolean recurringDelete = false;
    
    public String eventColor;
    
    int TimelineID = StartingModeController.timelineIdToModify;
    int EventID = StartingModeController.eventIdToModify;
    

    /**
     * Show alert message when user want to delete event. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void delete() throws IOException {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Delete");
    	alert.setHeaderText("Delete event");
    	
    	CheckBox checkBox = new CheckBox("Delete all recurring events");
    	alert.getDialogPane().setContent(checkBox);
    	
    	checkBox.setIndeterminate(false);
    	if (isEventRec(Main.project.getTimeline(TimelineID).getEvent(EventID))==0)
    		checkBox.setDisable(true);
    	
    	if (alert.showAndWait().get() == ButtonType.OK){
    		//Checks if the user wants to delete all recurring events.
        	if(checkBox.isSelected())
        		Main.project.getTimeline(TimelineID).removeAllEvents(allEventsRec(Main.project.getTimeline(TimelineID).getEvent(EventID)));
        	else
        		Main.project.getTimeline(TimelineID).removeEvent(EventID);
        	Stage stage = (Stage) btnSave.getScene().getWindow();
		    stage.close();
    	} 	

    }
    	
    	
    /**
     * Enables to set end time for duration.
     * @param event
     */
    @FXML
    void eventDuration(ActionEvent event) {
    	
    	this.duration = !this.duration;
    	endTextField.disableProperty().set(!endTextField.disableProperty().get());
    	endHH.setDisable(!endHH.isDisabled());
    	endMM.setDisable(!endMM.isDisabled());
    }

    /**
     * Change the image for the event.
     * @param event
     */
    @FXML
    void eventImage(MouseEvent event) {
    	
//    	Open window for selecting a new picture to event
    	File file;
		Stage stage = new Stage();
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		file = fileChooser.showOpenDialog(stage);
		
//		Changing the picture 
		URL url = null;
		
		try {
			url = file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
			
		eventImage_imageView.setImage(new Image(url.toExternalForm()));
		
    }

    /**
     * Gives the event a name.
     * @param event.
     * @return name of the event.
     */
    @FXML
    String nameEvent(ActionEvent event) {
    	String eventName;
    	eventName = NameEvent_textField.getText();
    	
    	return eventName;
    }

    /**
     * Select recurring for the event.
     * @param event.
     * @return the recurring of the event. 
     */
    @FXML
    public String recurring(ActionEvent event) {

    	return this.Reccuring_ComboBox.getValue();
    }

    /**
     * Select color for the event.
     * @param event.
     * @return returns the color of the event. 
     */
    @FXML
    void selectEventColor(ActionEvent event) {
    	eventColor = this.color_ComboBox.getValue();
    }

    /**
     * Gets end value from the date pricker
     * @param event
     */
    @FXML
    void setEventEnd(ActionEvent event) {
    	
    	this.endTextField.getValue();
    }
    
    /**
     * Gets start value from the date pricker
     * @param event
     */
    @FXML
    void setEventStart(ActionEvent event) {
    	
    	this.startTextField.getValue();
    }
    
    /**
     * Get value from start date of event.
     * @return value of start date for event.
     */
    public DatePicker getStartValue() {
    	return this.startTextField;
    }
    
    /**
     * Get value from end date of event.
     * @return value of end date for event.
     */
    public DatePicker getEndValue() {
    	return this.endTextField;
    }
    
    /**
     * Gives options in the comboBox
     */
    @FXML
    public void initialize() {
    	EventID=StartingModeController.eventIdToModify;
    	
    	if (EventID!=0){
    		Timeline t=Main.project.getTimeline(TimelineID);
    		Event e = t.getEvent(EventID);
    		if (isEventRec(e)!=0){
    			e=allEventsRec(e).get(0);
    			EventID=e.getEventId();
    		}
    		NameEvent_textField.setText(e.getTitle());
    		duration_checkBox.setSelected(e.isDurationEvent());
    		startTextField.setValue(e.getStartTime().toLocalDate());
    		startHH.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, e.getStartTime().toLocalTime().getHour()));
    		startMM.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, e.getStartTime().toLocalTime().getMinute()));
    		endTextField.setDisable(!e.isDurationEvent());
			endHH.setDisable(!e.isDurationEvent());
			endMM.setDisable(!e.isDurationEvent());
    		endTextField.setValue(startTextField.getValue());
    		description.setText(e.getDescription());
    		eventImage_imageView.setImage(e.getImage());
    		if (e.getColor().toString().equals(Color.RED.toString()))
    			color_ComboBox.setValue("Red");
    		else if (e.getColor().toString().equals(Color.BLUE.toString()))
    			color_ComboBox.setValue("Blue");
    		else if (e.getColor().toString().equals(Color.GREEN.toString()))
    			color_ComboBox.setValue("Green");
    		else if (e.getColor().toString().equals(Color.ORANGE.toString()))
    			color_ComboBox.setValue("Orange");
    		if (e.isDurationEvent()){
    			endTextField.setValue(e.getEndTime().toLocalDate());
    			endHH.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, e.getEndTime().toLocalTime().getHour()));
    			endMM.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, e.getEndTime().toLocalTime().getMinute()));
    		}
    		switch (isEventRec(e)){
    		case 0: Reccuring_ComboBox.setValue("Non"); break;
    		case 1: Reccuring_ComboBox.setValue("Every day"); break;
    		case 2: Reccuring_ComboBox.setValue("Every week"); break;
    		case 3: Reccuring_ComboBox.setValue("Every month"); break;
    		case 4: Reccuring_ComboBox.setValue("Every year"); break;
    		}
    		
    		delete_btn.setDisable(false);
    		
    	}
    	else{
    		NameEvent_textField.setText("");
    		duration_checkBox.setSelected(false);
    		startTextField.setValue(null);
    		startHH.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
    		startMM.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    		endTextField.setDisable(true);
			endTextField.setValue(null);
			endHH.setDisable(true);
			endHH.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
			endMM.setDisable(true);
			endMM.setValueFactory((SpinnerValueFactory<Integer>)new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
    		description.setText(null);
    		eventImage_imageView.setImage(null);
    		color_ComboBox.setValue("Red");
    		Reccuring_ComboBox.setValue("Non");
    	}
    	
    	color_ComboBox.setItems(color_Combo);
    	Reccuring_ComboBox.setItems(Reccuring_ComboBox_Value);
    }
    
    /**
     * Adds description to event
     * @return description of the event
     */
    @FXML
    public String decsription() {
    	String text;
    	text = description.getText();
    	
    	return text;
    }
    @FXML
    public void saveEvent() throws IOException {	// Add event
		Image image = eventImage_imageView.getImage();
    	
    	// Check if start and end date are selected
    	if (startTextField.getValue()==null || (endTextField.getValue()==null && duration_checkBox.isSelected() )){
			
			alertWindow(AlertType.ERROR, "ERROR!", "Cannot add event!", "End or Start date is not selected");
    	}
    	
    	// Check if end date is after or equals start date
    	else if (duration_checkBox.isSelected() && startTextField.getValue().isAfter(endTextField.getValue())){
    		
			alertWindow(AlertType.ERROR, "ERROR!", "Cannot add event!", "End date should be after or equals start date");
    	}
    	
    	// Check if the user enters an event's name
    	else if (NameEvent_textField.getText().replaceAll("\\s+","").isEmpty()){
    		
			alertWindow(AlertType.ERROR, "ERROR!", "Cannot add event!", "Event should have name");
    	}
    	
    	// Check if the user selects an event's color
    	else if (color_ComboBox.getValue()==null){
			
			alertWindow(AlertType.ERROR, "ERROR!", "Cannot add event!", "Please select a color");
    	}
    	
   
    	
    	// If all inputs are correct then add event
    	else {
    		Event e = new Event(null, null, null, null, null);
        	Color color = Color.valueOf(color_ComboBox.getValue());
        	
        	// Check if it is duration or non-duration event
        	if (duration_checkBox.isSelected()){
        		e = new DurationEvent(NameEvent_textField.getText(), LocalDateTime.of(startTextField.getValue(), LocalTime.of(startHH.getValue(), startMM.getValue())), LocalDateTime.of(endTextField.getValue(), LocalTime.of(endHH.getValue(), endMM.getValue())), description.getText(),image, color );
        	}
        	else{
        		e = new NonDurationEvent(NameEvent_textField.getText(), LocalDateTime.of(startTextField.getValue(), LocalTime.of(startHH.getValue(), startMM.getValue())), description.getText(),image, color );
        	}
        	if (EventID!=0){
        		Main.project.getTimeline(TimelineID).removeAllEvents(allEventsRec(Main.project.getTimeline(TimelineID).getEvent(EventID)));
        		e.setEventId(EventID);
        	}

        	
        	// Search for time line by its ID to add the event
        	for (Timeline temp : Main.project.getTimelines())
        		if (temp.getTimelineId()==TimelineID){
        			int initialSize = temp.getEvents().size();
        			while (temp.addEvent(e)){
        				if (e.isDurationEvent())
        					e=new DurationEvent((DurationEvent)e);
        				else
        					e=new NonDurationEvent((NonDurationEvent)e);

        				if (Reccuring_ComboBox.getValue()==null || Reccuring_ComboBox.getValue().equals("Non")){
            				break;
            			}
        				else if (Reccuring_ComboBox.getValue().equals("Every day")){
        					e.setStartTime(e.getStartTime().plusDays(1));
            				if (e.isDurationEvent()) e.setEndTime(e.getEndTime().plusDays(1));
        				}
        				else if (Reccuring_ComboBox.getValue().equals("Every week")){
        					e.setStartTime(e.getStartTime().plusWeeks(1));
            				if (e.isDurationEvent()) e.setEndTime(e.getEndTime().plusWeeks(1));
        				}
        				else if (Reccuring_ComboBox.getValue().equals("Every month")){
        					e.setStartTime(e.getStartTime().plusMonths(1));
            				if (e.isDurationEvent()) e.setEndTime(e.getEndTime().plusMonths(1));
        				}
        				else if (Reccuring_ComboBox.getValue().equals("Every year")){
        					e.setStartTime(e.getStartTime().plusYears(1));
            				if (e.isDurationEvent()) e.setEndTime(e.getEndTime().plusYears(1));
        				}	
        			}
        			if (temp.getEvents().size()==initialSize){
        				alertWindow(AlertType.ERROR, "ERROR!", "Cannot add event!", "Event's start-date is before timeline's start-date. Or Event's end-date is after timeline's end-date. ");
        				return;
        			}
        			Stage stage = (Stage) btnSave.getScene().getWindow();
    			    stage.close();
        		}
    	}
    	
    	
    }
    

    public void stop(){
        System.out.println("Stage is closing");
    }
    
    /**
     * Warning popUp 
     * @param type of alert
     * @param Title 
     * @param headText
     * @param contentText
     */
    public void alertWindow(AlertType type, String Title, String headText, String contentText) {
    	
    	Alert alert = new Alert(type);
		alert.setTitle(Title);
		alert.setHeaderText(headText);
		alert.setContentText(contentText);
		alert.showAndWait();
    	
    }
    
    private List <Event> allEventsRec(Event e){
    	List <Event> allEventsRec = new ArrayList<Event>();
    	Timeline t = Main.project.getTimeline(TimelineID);
    	for (Event temp : t.getEvents()){
    		System.out.println(temp.getTitle());
    		System.out.println(e.getTitle());
    		if (temp.getTitle().equals(e.getTitle()))
    			allEventsRec.add(temp);
    	}
    	System.out.println(allEventsRec.size());
    	return allEventsRec;
    }
    
    private int isEventRec(Event e){
    	List <Event> allEventsRec= allEventsRec(e);
    	if (allEventsRec.size()>1){
       		Period period = Period.between(allEventsRec.get(0).getStartTime().toLocalDate(), allEventsRec.get(1).getStartTime().toLocalDate());
    		System.out.println(period.getDays());
    		System.out.println(period.getMonths());
    		System.out.println(period.getYears());
    		System.out.println(period.getDays());
    		if (period.getDays()==Period.ofDays(1).getDays())
    			return 1;
    		else if (period.getDays() == Period.ofDays(7).getDays())
    			return 2;
    		else if (period.getMonths()==Period.ofMonths(1).getMonths() )
    			return 3;
    		else if (period.getYears() == Period.ofYears(1).getYears())
    			return 4;
    		else
    			return 0;
    		
    	}
    	else
    		return 0;
		
    }
}

