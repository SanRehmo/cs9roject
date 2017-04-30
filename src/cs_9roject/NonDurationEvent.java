package cs_9roject;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;

//This class to present Non-Duration Event
public class NonDurationEvent extends Event {
	
	
	// mine
    // Create new event with specific ID. Almost used when importing events from database
	public NonDurationEvent(int timelineID, int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, int imageID) {
		super(timelineID, eventID, eventTitle, eventStart_time, eventEnd_time, description, imageID);
		isDurationEvent=false;
	}
	
	// Create new event with new ID.
	public NonDurationEvent(String eventTitle, LocalDateTime eventStart_time, String description, Image EventImage, Color EventColor) {
		super(eventTitle, eventStart_time, description, EventImage, EventColor);
		isDurationEvent=false;
	}

}
