package cs_9roject;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationEvent extends Event {
	private Duration duration;
	
	private LocalDateTime endTime;

	public DurationEvent(int eventID, String eventTitle, java.time.LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, int imageID) {
		super(eventID, eventTitle, eventStart_time, eventEnd_time, description, imageID);
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
