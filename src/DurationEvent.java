package cs_9roject;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationEvent extends Event {
	private Duration duration;
	
	private LocalDateTime endTime;
	
	public DurationEvent(String Title, String Description,LocalDateTime StartTime, LocalDateTime EndtTime ){
		title=Title;
		description= Description;
		startTime=StartTime;
		endTime=EndtTime;
		duration = Duration.between(startTime, endTime);

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
	
	private void setDuration(Duration interval){
		duration = interval;
	}
	
	public Duration getDuration(){
		return duration;
	}

}
