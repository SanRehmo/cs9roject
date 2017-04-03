package cs9roject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Timelines {
	protected List<Timeline> timelines= new ArrayList<Timeline>();
	
	public Timelines(){
		
	}
	
	public void addTimeline(LocalDateTime StartTime, LocalDateTime EndTime){
		Timeline temp= new Timeline(StartTime, EndTime);
		timelines.add(temp);
	}
	
	public Timeline getTimeline(int ID){
		for (Timeline t : timelines ) 
			if (t.getTimelineId()==ID)
				return t;
		return null;
	}
	
	public void modifyTimeline(int ID, Timeline timeline){
		for (int i=0; i<timelines.size(); i++ ) 
			if (timelines.get(i).getTimelineId()==ID)
				timelines.set(i, timeline);
	}
	
	public void removeTimeline(int ID){
		for (int i=0; i<timelines.size(); i++ ) 
			if (timelines.get(i).getTimelineId()==ID)
				timelines.remove(i);
	}
	
	public List <Timeline> getTimelines(){
		return timelines;
	}
	
	public void removeAllTimelines(){
		timelines.removeAll(timelines);
	}
}
