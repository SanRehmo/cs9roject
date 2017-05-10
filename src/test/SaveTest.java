package test;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cs_9roject.DurationEvent;
import cs_9roject.Event;
import cs_9roject.NonDurationEvent;
import cs_9roject.Project;
import cs_9roject.Timeline;
import cs_9roject.TimelinesDAO;
import javafx.scene.paint.Color;

public class SaveTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		// Loding should be tested
		// we create manually project with specific timelines and events.
		//Then we save it to the database. Then we load it again check if stored project as exact created project. 
		TimelinesDAO dao=new TimelinesDAO();		
		Project project = new Project();
				project.ProjectID= 0;
				Project.setCount(dao.getHighestProjectID()+1);
				Event.setCount(dao.getHighestEventID() + 1);
				Timeline.setCount(dao.getHighestTimelineID() + 1);
				// Add 2 timlines to the object timelines. Every timeline has 2 events
				project.addTimeline(new Timeline(LocalDate.of(2017, 01, 01), LocalDate.of(2017, 12, 31), "Timeline1", false));
				project.addTimeline(new Timeline(LocalDate.of(2017, 06, 01), LocalDate.of(2018, 06, 30), "Timeline2", false));
				Event event1 = new DurationEvent ("event1",LocalDateTime.of(2017, 01, 10, 12, 00), LocalDateTime.of(2017, 01, 20, 12, 00),"test", null,Color.RED);
				Event event2 = new NonDurationEvent ("event2",LocalDateTime.of(2017, 06, 01, 12, 12),"test", null,Color.RED);
				Event event3 = new DurationEvent ("event3",LocalDateTime.of(2017,7, 10, 12, 00), LocalDateTime.of(2017, 8, 20, 12, 00),"test", null,Color.RED);
				Event event4 = new NonDurationEvent ("event4",LocalDateTime.of(2018, 01, 10, 12, 00),"test", null,Color.RED);
				project.getTimelines().get(0).addEvent(event1);
				project.getTimelines().get(0).addEvent(event2);
				project.getTimelines().get(1).addEvent(event3);
				project.getTimelines().get(1).addEvent(event4);
				
				
				// Save the project to database
				dao.save(project);
				
				// load the project from database
				Project loadedProject = dao.load(project.ProjectID);
				
				assertTrue (project.getTimelines().size()==loadedProject.getTimelines().size());
				
				for (int i=0; i<project.getTimelines().size(); i++ ){
					assertTrue (project.getTimelines().get(i).equals(loadedProject.getTimelines().get(i)));
					
					for (int j=0; j<project.getTimelines().get(i).getEvents().size(); j++)
						assertTrue (project.getTimelines().get(i).getEvents().get(j).equals(loadedProject.getTimelines().get(i).getEvents().get(j)));
				}
	}

}
