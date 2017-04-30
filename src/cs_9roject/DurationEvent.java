package cs_9roject;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.time.Duration;
import java.time.LocalDateTime;

//This class to present Duration Event
public class DurationEvent extends Event {
	private int lol;
	private Duration duration;
	
	private LocalDateTime endTime;
	
	// mine
    // Create new event with specific ID. Almost used when importing events from database
	public DurationEvent(int timelineID, int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, int imageID) {
		super(timelineID, eventID, eventTitle, eventStart_time, eventEnd_time, description, imageID);
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
