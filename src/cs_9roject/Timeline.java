package cs_9roject;


import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Timeline {
	private static int count=1;	// Variable to give unique ID for each Timeline
	protected int timelineId;
	protected LocalDate startDate;
	protected LocalDate endDate;
	protected Period duration;
	protected String title;
	protected Boolean isOnlyYears=false;	// Boolean variable to define timeline's type
	public List<Event> events = new ArrayList<Event>();
	
	
	// Create new timeline with specific ID and event-list. Almost used when importing timelines from database
	public Timeline(int timelineID, LocalDate StartDate, LocalDate EndDate, String Title, List<Event> Events) {
		timelineId = timelineID;
		startDate= StartDate;
		endDate= EndDate;
		title= Title;
		if (title.replaceAll("\\s+","").isEmpty()) title="timeline " + timelineId;
		duration = Period.between(startDate, endDate);
		for (Event e : Events) {
			if (this.getTimelineId() == e.timelineid)
				events.add(e);
		}
	}
	
	// Create new timeline with new ID and empty event-list.
	public Timeline(LocalDate StartDate, LocalDate EndDate, String Title, Boolean IsOnlyYears) {
		timelineId = count++;
		startDate= StartDate;
		endDate= EndDate;
		title= Title;
		if (title.replaceAll("\\s+","").isEmpty()) title="timeline " + timelineId;
		duration = Period.between(startDate, endDate);
		isOnlyYears=IsOnlyYears;
	}

	
	
	// Create new timeline without parameters (Default attributes)
	public Timeline(){
		timelineId = count++;
		startDate= LocalDate.now();
		endDate= startDate.plusDays(444);
		title= "timeline " + timelineId;
		duration = Period.between(startDate, endDate);
	}
	
	// Add new event to timeline
	public boolean addEvent(Event event){
		if ((event.isDurationEvent() && !event.getStartTime().toLocalDate().isBefore(startDate) && !((DurationEvent)event).getEndTime().toLocalDate().isAfter(endDate)) || (!event.isDurationEvent() && !event.getStartTime().toLocalDate().isBefore(startDate)&& !event.getStartTime().toLocalDate().isAfter(endDate))){
			events.add(event);
			return true;
		}
		return false; 
	}
	
	// get event from timeline
	public Event getEvent(int ID){
		for (Event e : events )
			if (e.getEventId()==ID)
				return e;
		return null;
	}
	
	// Remove event from timeline
	public void removeEvent(int ID){
		for (int i=0; i<events.size(); i++ ) 
			if (events.get(i).getEventId()==ID)
				events.remove(i);
	}
	
	// Modify event in timeline
	public void modifyEvent(int ID, Event event){
		for (int i=0; i<events.size(); i++ ) 
			if (events.get(i).getEventId()==ID)
				events.set(i, event);
	}
	
	// Remove list of events from timeline
	public void removeAllEvents(List <Event> Events){
		events.removeAll(Events);
	}
	
	
	
	// All next methods are (getters and setters)
	public List <Event> getEvents(){
		return events;
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
	
	@Override
	public String toString(){
		return title + " (" + events.size() + " event/s)" + timelineId;
	}
	
	public static void setCount(int Count){
		count = Count;
	}
	
	// Check if 2 timelines are equals
	public boolean equals(Timeline timeline){
		return startDate.equals(timeline.getStartDate()) && endDate.equals(timeline.getEndDate()) && title.equals(timeline.getTitle()) && timeline.getEvents().size()== this.getEvents().size();
	}
}
