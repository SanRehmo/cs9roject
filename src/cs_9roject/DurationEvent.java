package cs_9roject;

import java.time.Duration;
import java.time.LocalDateTime;

import javafx.scene.image.Image;

public class DurationEvent extends Event {
	private int some;
	private Duration duration;
	
	private LocalDateTime endTime;
	
	public DurationEvent(String Title, String Description,LocalDateTime StartTime, LocalDateTime EndtTime , Image EventImage ){
		title=Title;
		description= Description;
		startTime=StartTime;
		endTime=EndtTime;
		duration = Duration.between(startTime, endTime);
		eventImage=EventImage;

	}
	
	public void setStartTime(LocalDateTime StartTime){
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
	
	public void setDuration(Duration interval){
		duration = interval;
		endTime = startTime.plus(duration);
	}
	
	public Duration getDuration(){
		return duration;
	}

}
