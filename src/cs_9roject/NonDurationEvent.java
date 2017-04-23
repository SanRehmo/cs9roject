package cs_9roject;

import java.time.LocalDateTime;

import javafx.scene.image.Image;

public class NonDurationEvent extends Event {
	public NonDurationEvent(String Title, String Description,LocalDateTime StartTime, Image EventImage ){
		title=Title;
		description= Description;
		startTime=StartTime;
		isDurationEvent= false;
		eventImage= EventImage;

	}

}
