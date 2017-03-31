package cs_9roject;

import java.time.Duration;
import java.time.LocalDateTime;

public class NonDurationEvent extends Event {
	public NonDurationEvent(String Title, String Description,LocalDateTime StartTime ){
		title=Title;
		description= Description;
		startTime=StartTime;
		isDurationEvent= false;

	}

}
