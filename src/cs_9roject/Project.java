package cs_9roject;

import java.util.ArrayList;
import java.util.List;

// This class present object and save all timelines inside it
public class Project {

    protected List<Timeline> timelines = new ArrayList<Timeline>();
    protected static int count=1;
    public int ProjectID;
    public String projectName;

    // New project with new ID
	public Project() {
		ProjectID = count++;
		projectName = "Project " + ProjectID;
	}
	
	// to import project from database
    // Create new project with specific ID. Almost used when importing project from database
	public Project(int ID) {
		ProjectID = ID;
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
			return projectName + " (" + timelines.size() + " timeline/s)" + ProjectID;
		}
	
	public static void setCount(int Count){
		count = Count;
	}

	public static int getCount() {
		return count;
	}

	public int getProjectID() {
		return ProjectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setTimelines(List<Timeline> timelines) {
		this.timelines = timelines;
	}

	public void setProjectID(int projectID) {
		ProjectID = projectID;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
