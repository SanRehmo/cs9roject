package cs_9roject;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DurationEvent extends Event {
	private Duration duration;
	
	private LocalDateTime endTime;

	public DurationEvent(int eventID, String eventTitle, java.time.LocalDate eventStart_time, LocalDate eventEnd_time) {
		super(eventID, eventTitle, eventStart_time, eventEnd_time);
	}

	public void setStartTime(LocalDate StartTime) {
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
