package cs9roject;

import java.time.LocalDateTime;

public class TimelinesMain {
	static Timelines timelines = new Timelines();
	static TimelinesDAO dao= new TimelinesDAO();
	static String path = "c:\\tilelines.data";

	public static void main(String[] args) {
		
		// Add 2 timlines to the object timelines. Every timeline has 2 events
		LocalDateTime startTimeline= LocalDateTime.now();
		LocalDateTime endTimeline= startTimeline.plusDays(15);
		timelines.addTimeline(startTimeline, endTimeline);
		timelines.addTimeline(startTimeline.plusDays(2), endTimeline.plusDays(2));
		Event event1 = new DurationEvent ("event1","event1Description", startTimeline.plusDays(1),endTimeline.plusDays(2), null);
		Event event2 = new NonDurationEvent ("event2","event2Description", startTimeline.plusDays(3),null);
		Event event3 = new DurationEvent ("event3","event3Description", startTimeline.plusDays(4),endTimeline.plusDays(6), null);
		Event event4 = new NonDurationEvent ("event4","event4Description", startTimeline.plusDays(7), null);
		timelines.getTimelines().get(0).addEvent(event1);
		timelines.getTimelines().get(0).addEvent(event2);
		timelines.getTimelines().get(1).addEvent(event3);
		timelines.getTimelines().get(1).addEvent(event4);
		
		// Save timelines to database
		dao.save(path, timelines);
		
		// Load timelines from database
		Timelines timelines2= dao.load(path);
		
		timelines2= timelines;
		
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

}
