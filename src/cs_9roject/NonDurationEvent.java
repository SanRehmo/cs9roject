package cs_9roject;

import java.time.LocalDate;

public class NonDurationEvent extends Event {
	public NonDurationEvent(int eventID, String eventTitle, java.time.LocalDate eventStart_time, LocalDate eventEnd_time) {
		super(eventID, eventTitle, eventStart_time, eventEnd_time);

	}

}
