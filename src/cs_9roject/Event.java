package cs_9roject;

import javafx.scene.image.Image;

import java.time.LocalDateTime;

public class Event {
	private static int count=0;
	protected int eventid;
	protected String title= new String ();
	protected String description= new String ();
	protected LocalDateTime startTime;
	protected LocalDateTime endTime;
	protected boolean isDurationEvent= true;
	protected Image eventImage = null;

	public Event(int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time) {
		eventid = eventID;
		title = eventTitle;
		startTime = eventStart_time;
		endTime = eventEnd_time;

		eventID = count++;
	}
	
	public void setEventId(int ID){
		eventid = ID;
	}
	
	public int getEventId(){
		return eventid;
	}
	
	public void setTitle(String Title){
		title = Title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setDescription(String Description){
		description = Description;
	}
	
	public String getDescription(){
		return description;
	}

	public void setStartTime(LocalDateTime StartTime) {
		startTime = StartTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}
	
	public void setIsDurationEvent(boolean IsDurationEvent){
		isDurationEvent = IsDurationEvent;
	}
	
	public boolean isDurationEvent (){
		return isDurationEvent;
	}
	
	public void setImage(Image EventImage){
		eventImage = EventImage;
	}
	
	public Image getImage (){
		return eventImage;
	}

}
