package cs9roject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Timeline {
	private static int count=0;
	protected int timelineId;
	protected LocalDateTime startTime;
	protected LocalDateTime endTime;
	protected Duration duration;
	protected List<Event> events= new ArrayList<Event>();
	
	public Timeline(LocalDateTime StartTime, LocalDateTime EndTime){
		timelineId = count++;
		startTime= StartTime;
		endTime= EndTime;
		duration = Duration.between(startTime, endTime);
	}
	
	public void addEvent(Event event){
		events.add(event);
	}
	
	public Event getEvent(int ID){
		for (Event e : events ) 
			if (e.getEventId()==ID)
				return e;
		return null;
	}
	
	public void removeEvent(int ID){
		for (int i=0; i<events.size(); i++ ) 
			if (events.get(i).getEventId()==ID)
				events.remove(i);
	}
	
	public void modifyEvent(int ID, Event event){
		for (int i=0; i<events.size(); i++ ) 
			if (events.get(i).getEventId()==ID)
				events.set(i, event);
	}
	
	public List <Event> getEvents(){
		return events;
	}
	
	public void removeAllEvents(){
		events.removeAll(events);
	}
	
	public void setStartTime(LocalDateTime StartTime){
		startTime = StartTime;
		duration = Duration.between(startTime, endTime);
	}
	
	public LocalDateTime getStartTime(){
		return startTime;
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
	
	public void setTimelineId(int ID){
		timelineId = ID;
	}
	
	public int getTimelineId(){
		return timelineId;
	}
	
}
