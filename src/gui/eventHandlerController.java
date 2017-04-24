package gui;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class eventHandlerController {
	
	@FXML
	private ScrollPane start_scrollpane;
	
	boolean duration  = false;
	boolean recurring = false;
	
	ObservableList<String> color_Combo = FXCollections.observableArrayList(
			"Red", "Green", "Blue", "Orage");
	
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
    private TextField startTextField;

    @FXML
    private TextField endTextField;
    
    @FXML
    private TextField description;
    
    @FXML
    private ComboBox<String> Reccuring_ComboBox;

    /**
     * Show alert massage when activated
     * @param event
     * @throws IOException 
     */
    @FXML
    void delete(ActionEvent event) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("deleteEventPopUp.fxml"));
		Pane showEventHandler = loader.load();
		Stage stage2 = new Stage();
        stage2.setScene(new Scene(showEventHandler));  
        stage2.setTitle("Delete");
        stage2.show();	
    }
    	
    /**
     * Enables to set start and end time for duration;
     * @param event
     */
    @FXML
    void eventDuration(ActionEvent event) {
    	
    	if(this.duration == false) {
    		this.duration = true;
    		startTextField.disableProperty().set(false);
    		endTextField.disableProperty().set(false);
    	}
    	else {
    		this.duration = false;
    		startTextField.disableProperty().set(true);
    		endTextField.disableProperty().set(true);
    	}
    	
    }

    /**
     * Change the image for the event 
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
     * Gives the event a name 
     * @param event
     * @return name of the event 
     */
    @FXML
    String nameEvent(ActionEvent event) {
    	String eventName;
    	eventName = NameEvent_textField.getText();

    	System.out.print(eventName);
    	return eventName;
    }

    /**
     * Select recurring for the event
     * @param event
     * @return the recurring of the event 
     */
    @FXML
    public String recurring(ActionEvent event) {

    	return this.Reccuring_ComboBox.getValue();
    }

    /**
     * Select color for the event
     * @param event
     * @return returns the color of the event 
     */
    @FXML
    String selectEventColor(ActionEvent event) {
    	
    	return this.color_ComboBox.getValue();
    }

    @FXML
    void setEventEnd(ActionEvent event) {
    	
    	//TO-DO

    }
    

    @FXML
    void setEventStart(ActionEvent event) {
    	
    	//TO-DO 
    	

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
    public void saveEvent() throws IOException {
    	
    	
    	
    }
   
}

