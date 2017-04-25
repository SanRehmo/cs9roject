package cs_9roject;

import java.util.ArrayList;
import java.util.List;

public class Project {

	protected List<Timeline> timelines= new ArrayList<Timeline>();
	int ProjectID;


	public Project() {
		
	}

    public void addTimeline(Timeline timeline){
		timelines.add(timeline);
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
