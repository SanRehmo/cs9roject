package cs_9roject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TimelinesMain {
    static Project timelines = new Project();
    static TimelinesDAO dao= new TimelinesDAO();

	public static void main(String[] args) {


        // TESTING

        // Load project from database
        Project project = dao.load(3);

        // uncomment for testing
        // DB test for LOADING

        for (int i = 1; i < project.timelines.size(); i++) {

            Timeline timeline = project.getTimeline(i);

            // WORKS!
            System.out.println(timeline.getTitle());

            // WORKS!
            System.out.println(timeline.getEvent(i).getTitle());
            // System.out.println(timeline.getEvent(i).getDescription());
        }

        // run this to provoke the DB to get into connection refuse state due to too many connection attempts
        //
        // dao.stressTest();

        // Uncomment for testing
        // DB test for SAVING

        Project testProject = new Project();

        Event testEvent = new Event(10, "DB TEST", LocalDateTime.now(), LocalDateTime.of(2042, 01, 01, 12, 42, 42), "Used to test DB", 69);

        ArrayList<Event> testList = new ArrayList<Event>();

        testList.add(testEvent);

        LocalDate date = LocalDate.parse("2042-01-01");

        Timeline testTimeline = new Timeline(9, LocalDate.now(), date, "DB TEST", testList);

        testProject.ProjectID = 4;
        testProject.addTimeline(testTimeline);

        dao.save(testProject);

    }

}
