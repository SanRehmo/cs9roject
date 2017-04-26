package cs_9roject;

import java.time.LocalDateTime;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//This class to present Non-Duration Event
public class NonDurationEvent extends Event {
	
	
	// mine
    // Create new event with specific ID. Almost used when importing events from database
	public NonDurationEvent(int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, int imageID) {
		super(eventID, eventTitle, eventStart_time, eventEnd_time, description, imageID);
		isDurationEvent=false;
	}
	
	// Create new event with new ID.
	public NonDurationEvent(String eventTitle, LocalDateTime eventStart_time, String description, Image EventImage, Color EventColor) {
		super(eventTitle, eventStart_time, description, EventImage, EventColor);
		isDurationEvent=false;
	}

}
