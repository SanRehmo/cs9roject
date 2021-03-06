package cs_9roject;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.MalformedURLException;
import java.time.Duration;
import java.time.LocalDateTime;

//This class to present Duration Event
public class DurationEvent extends Event {
	private Duration duration;
	
	private LocalDateTime endTime;
	
	// to import events from database
    // Create new event with specific ID. Almost used when importing events from database
	public DurationEvent(int timelineID, int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, Color color, String imagepath) throws MalformedURLException {
		super(timelineID, eventID, eventTitle, eventStart_time, description, color, imagepath);
		endTime=eventEnd_time;
	}
	
	// Create new event with new ID.
		public DurationEvent(String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, Image EventImage, Color EventColor, String Imagepath) {
			super(eventTitle, eventStart_time, description, EventImage, EventColor, Imagepath);
			endTime = eventEnd_time;
		}
		
		// Create new event with new ID. copy all parameters from another event
		public DurationEvent(DurationEvent e) {
			super(e);
			endTime = e.endTime;
		}

	public void setStartTime(LocalDateTime StartTime) {
		startTime = StartTime;
		duration = Duration.between(startTime, endTime);
	}
	
	public void setEndTime(LocalDateTime EndTime){
		endTime = EndTime;
		duration = Duration.between(startTime, endTime);
	}
	
	public LocalDateTime getEndTime(){
		return endTime;
	}

	public Duration getDuration(){
		return duration;
	}

}
