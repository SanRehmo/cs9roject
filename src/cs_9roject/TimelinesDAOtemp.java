package cs_9roject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimelinesDAOtemp {
	
	static List<Project> projectList= new ArrayList<Project>(); 
	static Project timelines = new Project();
	static String path = "c:\\tilelines.data";
	
	public TimelinesDAOtemp(){
			
			// Add 2 timlines to the object timelines. Every timeline has 2 events
			LocalDate startTimeline= LocalDate.now();
			LocalDate endTimeline= startTimeline.plusDays(15);
			timelines.addTimeline(new Timeline (startTimeline, endTimeline, "Timeline1", false));
			timelines.addTimeline(new Timeline (startTimeline.plusDays(2), endTimeline.plusDays(2), "Timeline2", false));
			Event event1 = new DurationEvent ("event1", LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(2),"event1Description", null, null);
			Event event2 = new NonDurationEvent ("event2", LocalDateTime.now().plusDays(3), "event2Description",null, null);
			Event event3 = new DurationEvent ("event3", LocalDateTime.now().plusDays(4),LocalDateTime.now().plusDays(6),"event3Description", null, null);
			Event event4 = new NonDurationEvent ("event4", LocalDateTime.now().plusDays(7),"event4Description",null , null);
			timelines.getTimelines().get(0).addEvent(event1);
			timelines.getTimelines().get(0).addEvent(event2);
			timelines.getTimelines().get(1).addEvent(event3);
			timelines.getTimelines().get(1).addEvent(event4);
			
			
			
			Project timelines2= new Project();
			for (Timeline t : timelines.timelines )
				timelines2.timelines.add(t);
			
			projectList.add(timelines);
			projectList.add(timelines2);
			timelines2.addTimeline(new Timeline (startTimeline.plusDays(2), endTimeline.plusDays(2), "Timeline3", false));
			
			// Check if saved and loaded time lines are same. All printing should be True
			System.out.println((timelines.getTimelines().size()==timelines2.getTimelines().size()));
			System.out.println((timelines.getTimelines().get(0).getEvents().size()==(timelines2.getTimelines().get(0).getEvents().size())));
			System.out.println((timelines.getTimelines().get(1).getEvents().size()==(timelines2.getTimelines().get(1).getEvents().size())));
			
			System.out.println((timelines.getTimelines().get(0).getEvents().get(0).isDurationEvent==timelines2.getTimelines().get(0).getEvents().get(0).isDurationEvent));
			System.out.println((timelines.getTimelines().get(0).getEvents().get(1).isDurationEvent==timelines2.getTimelines().get(0).getEvents().get(1).isDurationEvent));
			System.out.println((timelines.getTimelines().get(1).getEvents().get(0).isDurationEvent==timelines2.getTimelines().get(1).getEvents().get(0).isDurationEvent));
			System.out.println((timelines.getTimelines().get(1).getEvents().get(1).isDurationEvent==timelines2.getTimelines().get(1).getEvents().get(1).isDurationEvent));
			
			System.out.println((timelines.getTimelines().get(0).getEvents().get(0).getDescription()==timelines2.getTimelines().get(0).getEvents().get(0).getDescription()));
			System.out.println((timelines.getTimelines().get(0).getEvents().get(1).getDescription()==timelines2.getTimelines().get(0).getEvents().get(1).getDescription()));
			System.out.println((timelines.getTimelines().get(1).getEvents().get(0).getDescription()==timelines2.getTimelines().get(1).getEvents().get(0).getDescription()));
			System.out.println((timelines.getTimelines().get(1).getEvents().get(1).getDescription()==timelines2.getTimelines().get(1).getEvents().get(1).getDescription()));
	}
	
	public Project load(int ID) {
		for (Project p : projectList){
			if (p.ProjectID== ID)
				return p;
		}
		return null;
	}
	
	public List<Project> loadAllProjects() {
		return projectList;
	}
	
	public void save(Project project) {
		for (Project p : projectList)
			if (p.ProjectID == project.ProjectID){
				p=project;
				return;
			}
		projectList.add(project);	
	}
	
	public void delete(Project project) {
		projectList.remove(project);
    }
	
	public void deleteAllProjects() {

        projectList = new ArrayList<Project>();
    }

}
