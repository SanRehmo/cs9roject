package gui;

import java.util.ArrayList;
import java.util.List;

import cs_9roject.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class showTimelinesController {
	
	public ScrollPane primaryScrollpane;

	List<String> names = new ArrayList<String>();


	VBox vbox = new VBox();
	
	 @FXML
	    public void initialize() {  //Reading every timeline and print there names in checkboxex 		 
		 for(int i=0; i<Main.project.getTimelines().size(); i++){
			 names.add(Main.project.getTimeline(i).getTitle());
			 HBox hbox = new HBox();
			 Pane pane = new Pane();
			 CheckBox cb = new CheckBox( Main.project.getTimeline(i).getTitle());
			 hbox.getChildren().addAll(cb);
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
    
    

}
