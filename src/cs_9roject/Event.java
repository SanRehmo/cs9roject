package cs_9roject;

import javafx.scene.image.Image;

import java.time.LocalDateTime;

// This class to present Event. it has 2 children: DurationEvent and NonDurationEvent
public class Event {
	private static int count=0;	// Variable to give unique ID for each Event
	protected int eventid;
	protected String title= new String ();
	protected String description= new String ();
	protected LocalDateTime startTime;
	protected LocalDateTime endTime;
    protected boolean isDurationEvent = true;
    protected int imageid = 0;
    protected Image eventImage = null;
    protected String imagepath = "";

    // mine
    // Create new event with specific ID. Almost used when importing events from database
    public Event(int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String Description, int imageID) {

		eventid = eventID;
		title = eventTitle;
		startTime = eventStart_time;
		endTime = eventEnd_time;
        description = Description;
        imageid = imageID;
    }
	
	// Create new event with new ID.
	public Event(String eventTitle, LocalDateTime eventStart_time, String Description, Image EventImage) {
		eventid = count++;
		title = eventTitle;
		startTime = eventStart_time;
		description=Description;
		eventImage=EventImage;
	}

    // all next methods are (getters and setters)
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
