package cs_9roject;


public class TimelinesMain {
    static Project timelines = new Project();
    static TimelinesDAO dao= new TimelinesDAO();

	public static void main(String[] args) {


        // TESTING

        // Load project from database
        Project project = dao.load(3);
        System.out.println("SIZE: " + project.getTimelines().size());

        // uncomment for testing
        // DB test for LOADING

        for (int i = 0; i < project.timelines.size(); i++) {

            Timeline timeline = project.getTimeline(project.getTimelines().get(i).getTimelineId());

            // WORKS!
            System.out.println("ID: " + timeline.getTimelineId() + ", TITLE: " + timeline.getTitle());


            for (Event event : timeline.events) {
                // EXTREMELY IMPORTANT LINE
                if (timeline.getTimelineId() == event.timelineid)
                    System.out.println("EV_ID: " + event.getEventId() + ", EV_TITLE: " + event.getTitle());
            }


            // WORKS!
            // System.out.println(timeline.getEvent(i).getTitle());
            //System.out.println(timeline.getEvent(i).getDescription());


/*
                for (Event event : timeline.getEvents()) {

                    Event tmp = timeline.getEvents().get(k);
                    System.out.println("EVENT ID: " + tmp.eventid + ", EVENT TITLE: " + tmp.title);
                }
*/
        }

        // uncomment for testing
        // DB test for DELETING
        /*

        Event event = project.getTimeline(4).getEvent(4);
        System.out.println("=====BEFORE DELETING=====");
        System.out.println("EVENT_ID: " + event.eventid);

        // WORKS
        dao.delete(event);
        System.out.println("=====AFTER DELETING=====");
        System.out.println("EVENT_ID: " + event.eventid);

        */


        // run this to provoke the DB to get into connection refuse state due to too many connection attempts
        //
        // dao.stressTest();

        // Uncomment for testing
        // DB test for SAVING
        /*
        Project testProject = new Project();

        Event testEvent = new Event(10, "DB TEST", LocalDateTime.now(), LocalDateTime.of(2042, 01, 01, 12, 42, 42), "Used to test DB", 69);

        ArrayList<Event> testList = new ArrayList<Event>();

        testList.add(testEvent);

        LocalDate date = LocalDate.parse("2042-01-01");

        Timeline testTimeline = new Timeline(9, LocalDate.now(), date, "DB TEST", testList);

        testProject.ProjectID = 4;
        testProject.addTimeline(testTimeline);

        dao.save(testProject);
        */
    }

}
