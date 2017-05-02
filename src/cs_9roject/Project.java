package cs_9roject;

import java.util.ArrayList;
import java.util.List;

// This class present object and save all timelines inside it
public class Project {

    protected List<Timeline> timelines = new ArrayList<Timeline>();
    protected static int count=0;
    int ProjectID;
    public String projectName;
    ArrayList<Timeline> timelineList = new ArrayList<Timeline>();


	public Project() {
		ProjectID= count++;
		projectName = "Project " + ProjectID;
	}
	
	// Add new timeline to project
    public void addTimeline(Timeline timeline){
		timelines.add(timeline);
	}
	
    // Return specific timeline in project by its ID
	public Timeline getTimeline(int ID){
		for (Timeline t : timelines ) 
			if (t.getTimelineId()==ID)
				return t;
		return null;
	}
	
	// Modify timeline in the project
	public void modifyTimeline(int ID, Timeline timeline){
		for (int i=0; i<timelines.size(); i++ ) 
			if (timelines.get(i).getTimelineId()==ID)
				timelines.set(i, timeline);
	}
	
	// Remove specific timeline from project
	public void removeTimeline(int ID){
		for (int i=0; i<timelines.size(); i++ ) 
			if (timelines.get(i).getTimelineId()==ID)
				timelines.remove(i);
	}
	
	// Return all timelines in project
	public List <Timeline> getTimelines(){
		return timelines;
	}
	
	// Remove all timelines from project
	public void removeAllTimelines(){
		timelines.removeAll(timelines);
	}
	
	@Override
		public String toString(){
			return projectName + " (" + timelines.size() + " timeline/s)";
		}
}
