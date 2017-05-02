package cs_9roject;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.sql.Date;
import java.time.LocalDateTime;



// This class to present Event. it has 2 children: DurationEvent and NonDurationEvent
public class Event {
	private static int count=1;	// Variable to give unique ID for each Event
	protected int eventid;
	protected String title= new String ();
	protected String description= new String ();
	protected LocalDateTime startTime;
	protected LocalDateTime endTime;
    protected Date startDate;
    protected Date endDate;
    protected boolean isDurationEvent = true;
    protected int imageid = 0;
    protected Image eventImage = null;
    protected String imagepath = "";
    protected Color eventColor=Color.RED;
	protected int timelineid;

    // mine
    // Create new event with specific ID. Almost used when importing events from database
	public Event(int timelineID, int eventID, String eventTitle, LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String Description, int imageID) {

		timelineid = timelineID;
		eventid = eventID;
		title = eventTitle;
		startTime = eventStart_time;
		endTime = eventEnd_time;
        description = Description;
        imageid = imageID;
        startDate = Date.valueOf(eventStart_time.toLocalDate());
        endDate = Date.valueOf(eventEnd_time.toLocalDate());
    }
	
	// Create new event with new ID.
	public Event(String eventTitle, LocalDateTime eventStart_time, String Description, Image EventImage, Color EventColor) {
		eventid = count++;
		title = eventTitle;
		startTime = eventStart_time;
		description=Description;
		eventImage=EventImage;
		eventColor=EventColor;
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
	
	public void setEndTime(LocalDateTime EndTime) {
		endTime = EndTime;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
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
	

	public void setColor(Color EventColor){
		eventColor = EventColor;
	}
	
	public Color getColor (){
		return eventColor;
	}
	
	public static void setCount(int Count){
		count = Count;
	}

}
