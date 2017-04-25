package cs_9roject;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Timeline {
	private static int count=0;
	protected int timelineId;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected Period duration;
	protected String title;
	protected Boolean isOnlyYears=false;
	
	List<Event> events = new ArrayList<Event>();

	public Timeline(LocalDate StartDate, LocalDate EndDate, String Title, List<Event> Events) {
		timelineId = count++;
		startDate= StartDate;
		endDate= EndDate;
		title= Title;
		if (title==null) title="timeline " + timelineId;
		duration = Period.between(startDate, endDate);
		events=Events;
	}
	
	public Timeline(LocalDate StartDate, LocalDate EndDate, String Title, Boolean IsOnlyYears) {
		timelineId = count++;
		startDate= StartDate;
		endDate= EndDate;
		title= Title;
		if (title==null) title="timeline " + timelineId;
		duration = Period.between(startDate, endDate);
		isOnlyYears=IsOnlyYears;
	}


	public Timeline(){
		timelineId = count++;
		startDate= LocalDate.now();
		endDate= startDate.plusDays(444);
		title= "timeline " + timelineId;
		duration = Period.between(startDate, endDate);
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
	
	public void setStartTime(LocalDate StartDate){
		startDate = StartDate;
		duration = Period.between(startDate, endDate);
	}
	
	public LocalDate getStartDate(){
		return startDate;
	}
	
	public void setEndDate(LocalDate EndDate){
		endDate = EndDate;
		duration = Period.between(startDate, endDate);
	}
	
	public LocalDate getEndDate(){
		return endDate;
	}
	
	public void setDuration(Period interval){
		duration = interval;
		endDate = startDate.plus(duration);
	}
	
	public Period getDuration(){
		return duration;
		
	}
	
	public void setTitle(String Title){
		title = Title;
	}
	
	public String getTitle(){
		return title;
		
	}
	
	public void setTimelineId(int ID){
		timelineId = ID;
	}
	
	public int getTimelineId(){
		return timelineId;
	}
	
	public void setIsOnlyYears(Boolean IsOnlyYears){
		isOnlyYears = IsOnlyYears;
	}
	
	public Boolean getIsOnlyYears(){
		return isOnlyYears;
	}
	
	public boolean equals(Timeline timeline){
		return startDate.equals(timeline.getStartDate()) && endDate.equals(timeline.getEndDate()) && title.equals(timeline.getTitle());
	}
	
	
}
