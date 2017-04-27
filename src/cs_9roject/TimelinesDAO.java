package cs_9roject;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimelinesDAO {

    private Connection connection = null;
    List<List<Event>> listOfEventLists = new ArrayList<List<Event>>();

    // used to load a project from the DB through an ID
    public Project load(int ID) {

        Statement stmt = null;
        Project result = new Project();
        Timeline timeline = null;
        ArrayList<Event> eventList = new ArrayList<Event>();

        // attempt to establish a connection to the DB
        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (connection != null) {

            try {

                int count = 0;

                // load all events in the project
                stmt = connection.createStatement();
                ResultSet rss = stmt.executeQuery("SELECT Timelines.TIMELINE_ID, Events.EVENT_ID, Events.Title, Events.START_DATE, Events.END_DATE, Events.START_TIME, Events.END_TIME, Events.DESCRIPTION, Events.IMAGE_ID"
                        + " FROM Projects JOIN (Timelines, Events) ON Projects.TIMELINE_ID=Timelines.TIMELINE_ID WHERE Timelines.EVENT_ID=Events.EVENT_ID AND PROJECT_ID=" + ID);

                while (rss.next()) {

                    int eventID = rss.getInt("EVENT_ID");
                    String eventTitle = rss.getString("TITLE");
                    LocalDateTime eventStart_time = LocalDateTime.of(rss.getDate("START_DATE").toLocalDate(), rss.getTime("START_TIME").toLocalTime());
                    LocalDateTime eventEnd_time = LocalDateTime.of(rss.getDate("END_DATE").toLocalDate(), rss.getTime("END_TIME").toLocalTime());
                    int eventImageID = rss.getInt("IMAGE_ID");
                    String eventDescription = rss.getString("DESCRIPTION");
                    int timelineID = rss.getInt("TIMELINE_ID");

                    Event event = new Event(timelineID, eventID, eventTitle, eventStart_time, eventEnd_time, eventDescription, eventImageID);
                    eventList.add(event);
                }


                // load all timelines in the project
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Timelines.TIMELINE_ID, Timelines.EVENT_ID, Timelines.START_DATE, Timelines.END_DATE, Timelines.TITLE"
                        + " FROM Projects JOIN (Timelines, Events) ON Projects.TIMELINE_ID=Timelines.TIMELINE_ID WHERE Timelines.EVENT_ID=Events.EVENT_ID AND PROJECT_ID=" + ID);

                while (rs.next()) {


                    int timelineID = rs.getInt("TIMELINE_ID");
                    LocalDate timelineStart = rs.getDate("START_DATE").toLocalDate();
                    LocalDate timelineEnd = rs.getDate("END_DATE").toLocalDate();
                    String timelineTitle = rs.getString("TITLE");

                    timeline = new Timeline(timelineID, timelineStart, timelineEnd, timelineTitle, eventList);

                    result.addTimeline(timeline);

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection");
        }
        return result;
    }


    // load all Projects at once
    public List<Project> loadAllProjects() {

        List<Project> result = new ArrayList<Project>();
        Connection connection = null;
        Statement stmt = null;
        Timeline timeline = null;

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (connection != null) {
            String query = "SELECT * FROM Projects";
            try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                int count = 1;
                while (rs.next()) {
                    result.add(load(count));
                    count++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Failed to make connection");
        }
        return result;
    }


    // save a Project
    public void save(Project project) {

        if (isConnected()) {

            // Projects loop
            for (int i = 0; i < project.timelines.size(); i++) {

                Timeline tl = project.timelines.get(i);
                String projects = "INSERT INTO Projects " + "VALUES (" + project.ProjectID + ", " + project.timelines.get(i).timelineId + ")";
                execute(projects);


                // Timelines & Events loop
                for (int j = 0; j < tl.events.size(); j++) {


                    Event ev = tl.events.get(j);

                    // extracting Date and Time from LocalDateTime
                    Date startDate = Date.valueOf(tl.startDate);
                    Date endDate = Date.valueOf(tl.endDate);

                    String timelines = "INSERT INTO Timelines " + "VALUES (" + tl.timelineId + ", " + ev.eventid + ", '" + startDate + "', '" + endDate + "', '" + tl.title + "')";
                    execute(timelines);

                    // extracting Date and Time from LocalDateTime
                    startDate = Date.valueOf(ev.startTime.toLocalDate());
                    Time startTime = Time.valueOf(ev.startTime.toLocalTime());
                    endDate = Date.valueOf(ev.endTime.toLocalDate());
                    Time endTime = Time.valueOf(ev.endTime.toLocalTime());

                    String events = "INSERT INTO Events " + "VALUES (" + ev.eventid + ", '" + ev.title + "', '" + startTime + "', '" + endTime + "', '" + startDate + "', '" + endDate + "', '" + ev.description + "', " + ev.imageid + ")";
                    execute(events);


                }
            }
        }
    }

    // helper method to execute a query on the DB
    public void execute(String query) {


        Statement stmt = null;

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (connection != null) {

            try {
                stmt = connection.createStatement();
                stmt.executeUpdate(query);
                System.out.println(query + " executed.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // helper method to check if JDBC is connectedd
    public boolean isConnected() {
        return (connection != null);
    }

    // helper method to stress test the DB with infinite queries
    // provokes connection refusal from DB
    public void stressTest() {

        String query = "SELECT * FROM Projects";

        if (isConnected()) {
            while (true) {
                execute(query);
            }
        }
    }
}

