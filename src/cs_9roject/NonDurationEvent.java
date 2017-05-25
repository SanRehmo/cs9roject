package cs_9roject;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.MalformedURLException;
import java.time.LocalDateTime;

//This class to present Non-Duration Event
public class NonDurationEvent extends Event {
	
	
	// to import events from database
    // Create new event with specific ID. Almost used when importing events from database
	public NonDurationEvent(int timelineID, int eventID, String eventTitle, LocalDateTime eventStart_time, String description, Color color, String imagepath) throws MalformedURLException {
		super(timelineID, eventID, eventTitle, eventStart_time, description, color, imagepath);
		isDurationEvent=false;
	}
	
	// Create new event with new ID.
	public NonDurationEvent(String eventTitle, LocalDateTime eventStart_time, String description, Image EventImage, Color EventColor, String Imagepath) {
		super(eventTitle, eventStart_time, description, EventImage, EventColor, Imagepath);
		isDurationEvent=false;
	}
	
	// Create new event with new ID. copy all parameters from another event
	public NonDurationEvent(NonDurationEvent e) {
		super(e);
		isDurationEvent=false;
	}

}
