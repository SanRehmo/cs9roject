package cs_9roject;

import java.time.LocalDateTime;

import javafx.scene.image.Image;

public class Event {
	private static int count=0;
	protected int eventId;
	protected String title= new String ();
	protected String description= new String ();
	protected LocalDateTime startTime;
	protected boolean isDurationEvent= true;
	protected Image eventImage = null;
	
	public Event(){
		eventId= count++;
	}
	
	public void setEventId(int ID){
		eventId = ID;
	}
	
	public int getEventId(){
		return eventId;
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
	
	public void setStartTime(LocalDateTime StartTime){
		startTime = StartTime;
	}
	
	public LocalDateTime getStartTime(){
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
