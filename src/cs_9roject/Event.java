package cs_9roject;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDateTime;



// This class to present Event. it has 2 children: DurationEvent and NonDurationEvent
public class Event {
	private static int count=1;	// to give unique ID for each Event
	protected int eventid;
	protected String title= new String ();
	protected String description= "";
	protected LocalDateTime startTime;
    protected boolean isDurationEvent = true;
    protected Image eventImage = null;
    protected String imagepath = "";
    protected Color eventColor=Color.RED;
	protected int timelineid;

    // to import events from database
    // Create new event with specific ID. Almost used when importing events from database
	public Event(int timelineID, int eventID, String eventTitle, LocalDateTime eventStart_time, String Description, Color color, String Imagepath) throws MalformedURLException {
		timelineid = timelineID;
		eventid = eventID;
		title = eventTitle;
		startTime = eventStart_time;
        description = Description;
        if (description.equals("null")) description="";
        imagepath = Imagepath;
        if (imagepath.equals("null")) imagepath="";
        this.setImage(imagepath);
        eventColor=color;
    }
	
	// Create new event with new ID.
	public Event(String eventTitle, LocalDateTime eventStart_time, String Description, Image EventImage, Color EventColor, String Imagepath) {
		eventid = count++;
		title = eventTitle;
		startTime = eventStart_time;
		description=Description;
		eventImage=EventImage;
		imagepath=Imagepath;
		eventColor=EventColor;
	}
	
	// Create new event with new ID. copy all parameters from another event
	public Event(Event e) {
		eventid = count++;
		title = e.title;
		startTime = e.startTime;
		description=e.description;
		eventImage=e.eventImage;
		eventColor=e.eventColor;
		eventImage=e.eventImage;
		imagepath= e.imagepath;
	}
	
	// Compare 2 events if they equal each other
	public boolean equals(Event event){
		return (event.getTitle().equals(title) && event.getStartTime().isEqual(startTime) && event.getDescription().equals(description) && event.getColorName().equals(this.getColorName())&& event.imagepath.equals(imagepath)&& event.isDurationEvent()==this.isDurationEvent());
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
	
	private void setImage(String imagePath) throws MalformedURLException  {
		if (!imagePath.isEmpty()){
			File file = new File(imagePath);
			eventImage = new Image(file.toURI().toURL().toExternalForm());
		}
		else
			eventImage = null;
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
	
	@Override
	public String toString(){
		return title + " ID: " + eventid;
	}
	
	// Convert the color from hex to name
	public String getColorName(){
		if (eventColor.toString().equals(Color.RED.toString()))
			return("Red");
		else if (eventColor.toString().equals(Color.BLUE.toString()))
			return("Blue");
		else if (eventColor.toString().equals(Color.GREEN.toString()))
			return("Green");
		return("Orange");
		
	}

}
