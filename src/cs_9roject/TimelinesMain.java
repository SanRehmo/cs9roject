package cs_9roject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimelinesMain {
    static Project timelines = new Project();
    static TimelinesDAO dao= new TimelinesDAO();

	public static void main(String[] args) {
		
		// Add 2 timlines to the object timelines. Every timeline has 2 events
		LocalDate startTimeline= LocalDate.now();
		LocalDate endTimeline= startTimeline.plusDays(15);
		timelines.addTimeline(startTimeline, endTimeline, "Timeline1");
		timelines.addTimeline(startTimeline.plusDays(2), endTimeline.plusDays(2), "Timeline2");
		Event event1 = new DurationEvent ("event1","event1Description", LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(2), null);
		Event event2 = new NonDurationEvent ("event2","event2Description", LocalDateTime.now().plusDays(3),null);
		Event event3 = new DurationEvent ("event3","event3Description", LocalDateTime.now().plusDays(4),LocalDateTime.now().plusDays(6), null);
		Event event4 = new NonDurationEvent ("event4","event4Description", LocalDateTime.now().plusDays(7), null);
		timelines.getTimelines().get(0).addEvent(event1);
		timelines.getTimelines().get(0).addEvent(event2);
		timelines.getTimelines().get(1).addEvent(event3);
		timelines.getTimelines().get(1).addEvent(event4);
		
		// Save timelines to database
        // dao.save(path, timelines);

        // Load timelines from database
        Project timelines2 = dao.load(1);

        // Save timeline to database

        ArrayList<Integer> timelineIDs = new ArrayList<>();
        timelineIDs.add(2);
        timelineIDs.add(3);
        timelineIDs.add(4);
        Project timelines3 = new Project();
        timelines3.ProjectID = 3;
        timelines3.TimelineID = timelineIDs;

        dao.save(timelines3);

        // timelines2= timelines;

        /*
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
*/

        // DB test for LOADING
        System.out.println(timelines2.timelines.get(0).title);

        // DB test for SAVING
        System.out.println();

    }

}
