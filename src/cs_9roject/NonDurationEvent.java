package cs_9roject;

import java.time.LocalDateTime;

public class NonDurationEvent extends Event {
	public NonDurationEvent(int eventID, String eventTitle, java.time.LocalDateTime eventStart_time, LocalDateTime eventEnd_time, String description, int imageID) {
		super(eventID, eventTitle, eventStart_time, eventEnd_time, description, imageID);

	}

}
