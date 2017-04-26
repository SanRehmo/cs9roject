package gui;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import cs_9roject.DurationEvent;
import cs_9roject.Event;
import cs_9roject.NonDurationEvent;
import cs_9roject.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class eventHandlerController {
	
	@FXML
	private ScrollPane start_scrollpane;
	
	boolean duration  = false;
	boolean recurring = false;
	
	ObservableList<String> color_Combo = FXCollections.observableArrayList(
			"Red", "Green", "Blue", "Orange");
	
	ObservableList<String> Reccuring_ComboBox_Value = FXCollections.observableArrayList(
			"Every day", "Every week", "Every month", "Every year");

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
    private DatePicker endTextField;
    
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<String> Reccuring_ComboBox;
    
    public static boolean recurringDelete = false;

    /**
     * Show alert message when user want to delete event. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void delete(ActionEvent event) throws IOException {
    	
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Delete");
    	alert.setHeaderText("Delete event");
    	
    	CheckBox checkBox = new CheckBox("Delete all recurring events");
    	alert.getDialogPane().setContent(checkBox);
    	
    	checkBox.setIndeterminate(false);
    	
//    	Checks if the user wants to delete all recurring events.
    	if(checkBox.isSelected())
    		eventHandlerController.recurringDelete = true;
    	
    	alert.showAndWait();

    }
    	
    	
    /**
     * Enables to set end time for duration.
     * @param event
     */
    @FXML
    void eventDuration(ActionEvent event) {
    	
    	if(this.duration == false) {
    		this.duration = true;
    		endTextField.disableProperty().set(false);
    	}
    	else {
    		this.duration = false;
    		endTextField.disableProperty().set(true);
    	}
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

    	System.out.print(eventName);
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
    String selectEventColor(ActionEvent event) {
    	
    	return this.color_ComboBox.getValue();
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
    private void initialize() {
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
    	int TimelineID=0;
    	Image image = eventImage_imageView.getImage();
    	
    	// Check if start and end date are selected
    	if (startTextField.getValue()==null || (endTextField.getValue()==null && duration_checkBox.isSelected() )){
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot add event!");
			alert.setContentText("End or Start date is not selected");
			alert.showAndWait();
    	}
    	
    	// Check if end date is after or equals start date
    	else if (duration_checkBox.isSelected() && startTextField.getValue().isAfter(endTextField.getValue())){
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot add event!");
			alert.setContentText("End date should be after or equals start date");
			alert.showAndWait();
    	}
    	
    	// Check if the user enters an event's name
    	else if (NameEvent_textField.getText().replaceAll("\\s+","").isEmpty()){
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot add event!");
			alert.setContentText("Event should have name");
			alert.showAndWait();
    	}
    	
    	// Check if the user selects an event's color
    	else if (color_ComboBox.getValue()==null){
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Cannot add event!");
			alert.setContentText("Please select a color");
			alert.showAndWait();
    	}
    	
    	// If all inputs are correct then add event
    	else {
    		Event e = new Event(null, null, null, null, null);
        	Color color = Color.valueOf(color_ComboBox.getValue());
        	
        	// Check if it is duration or non-duration event
        	if (duration_checkBox.isSelected()){
        		e = new DurationEvent(NameEvent_textField.getText(), LocalDateTime.of(startTextField.getValue(), LocalTime.of(00, 00)), LocalDateTime.of(endTextField.getValue(), LocalTime.of(00, 00)), description.getText(),image, color );
        	}
        	else{
        		e = new NonDurationEvent(NameEvent_textField.getText(), LocalDateTime.of(startTextField.getValue(), LocalTime.of(00, 00)), description.getText(),image, color );
        	}
        	
        	// Search for time line by its ID to add the event
        	for (Timeline temp : Main.project.getTimelines())
        		if (temp.getTimelineId()==TimelineID){
        			temp.addEvent(e);
        			Stage stage = (Stage) btnSave.getScene().getWindow();
    			    stage.close();
        		}
    	}
    	
    	
    }
}

