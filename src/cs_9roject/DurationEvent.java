package cs_9roject;

import java.time.Duration;
import java.time.LocalDateTime;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

//This class to present Duration Event
public class DurationEvent extends Event {
	private Duration duration;
	
	private LocalDateTime endTime;
	
	// mine
    // Create new event with specific ID. Almost used when importing events from database
	public DurationEvent(int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, int imageID) {
		super(eventID, eventTitle, eventStart_time, eventEnd_time, description, imageID);
	}
	
	// Create new event with new ID.
		public DurationEvent(String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, Image EventImage, Color EventColor) {
			super(eventTitle, eventStart_time, description, EventImage, EventColor);
			endTime = eventEnd_time;
			imageid=eventid;
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

	/*
	public void setDuration(Duration interval){
		duration = interval;
		endTime = startTime.plus(duration);
	}
	*/

	public Duration getDuration(){
		return duration;
	}

}
