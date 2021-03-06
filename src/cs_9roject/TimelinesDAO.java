package cs_9roject;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimelinesDAO {

    private Connection connection;
    private String query;
    private Statement stmt;

    /**
     * Loads a Project from the database. Projects include Timelines which include Events
     * @param ID
     * @return Project
     */
    public Project load(int ID) {

        Project result = new Project(ID);
        Timeline timeline = null;
        ArrayList<Event> eventList = new ArrayList<>();

        if (connect()) {

            try {

                // load all events in the project
                stmt = connection.createStatement();
                ResultSet rss = stmt.executeQuery("SELECT Projects.PROJECT_NAME, Timelines.TIMELINE_ID, Events.EVENT_ID, Events.Title, Events.START_DATE, Events.END_DATE, Events.START_TIME, Events.END_TIME, Events.DESCRIPTION, Events.IMAGE_PATH, Events.DURATIONEVENT, Events.COLOR"
                                                    + " FROM Projects JOIN (Timelines, Events) ON Projects.TIMELINE_ID=Timelines.TIMELINE_ID WHERE Timelines.EVENT_ID=Events.EVENT_ID AND PROJECT_ID=" + ID);

                while (rss.next()) {

                    int eventID = rss.getInt("EVENT_ID");
                    String eventTitle = rss.getString("TITLE");
                    LocalDateTime eventStart_time = LocalDateTime.of(rss.getDate("START_DATE").toLocalDate(), rss.getTime("START_TIME").toLocalTime());
                    LocalDateTime eventEnd_time = LocalDateTime.of(rss.getDate("END_DATE").toLocalDate(), rss.getTime("END_TIME").toLocalTime());
                    String imagePath = rss.getString("IMAGE_PATH");
                    String eventDescription = rss.getString("DESCRIPTION");
                    int timelineID = rss.getInt("TIMELINE_ID");
                    boolean isDurationEvent = rss.getBoolean("DURATIONEVENT");
                    Color color = Color.valueOf(rss.getString("COLOR"));

                    if (isDurationEvent){
                        eventList.add(new DurationEvent(timelineID, eventID, eventTitle, eventStart_time, eventEnd_time, eventDescription, color, imagePath));
                    }
                    else{
                        eventList.add(new NonDurationEvent(timelineID, eventID, eventTitle, eventStart_time, eventDescription, color, imagePath));
                    }
                }

                // load all timelines in the project
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Timelines.TIMELINE_ID, Timelines.EVENT_ID, Timelines.START_DATE, Timelines.END_DATE, Timelines.TITLE"
                                                    + " FROM Timelines JOIN Projects ON Projects.TIMELINE_ID=Timelines.TIMELINE_ID WHERE PROJECT_ID=" + ID);

                while (rs.next()) {

                    if (timeline == null || rs.getInt("TIMELINE_ID") != timeline.getTimelineId()) {
                        int timelineID = rs.getInt("TIMELINE_ID");
                        LocalDate timelineStart = rs.getDate("START_DATE").toLocalDate();
                        LocalDate timelineEnd = rs.getDate("END_DATE").toLocalDate();
                        String timelineTitle = rs.getString("TITLE");
                        timeline = new Timeline(timelineID, timelineStart, timelineEnd, timelineTitle, eventList);
                        result.addTimeline(timeline);
                    }
                }

                // retrieving ProjectName seperately
                stmt = connection.createStatement();
                ResultSet rsss = stmt.executeQuery("SELECT Projects.PROJECT_NAME FROM Projects JOIN Timelines ON Projects.TIMELINE_ID=Timelines.TIMELINE_ID WHERE PROJECT_ID=" + ID);
                rsss.next();
                result.setProjectName(rsss.getString("PROJECT_NAME"));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return result;
    }

    /**
     * Return all Project names in the database
     * @return List<Pair<Integer, String>></Integer,> <Event>
     */
    public List<Pair<Integer, String>> loadAllProjectNames() {

        List<Pair<Integer, String>> result = new ArrayList<>();

        if (connect()) {
            try {
                stmt = connection.createStatement();
                String temp = "SELECT DISTINCT PROJECT_NAME, PROJECT_ID FROM Projects";
                ResultSet rss = stmt.executeQuery(temp);
                while(rss.next()) {
                    result.add(new Pair<>(rss.getInt("PROJECT_ID"), rss.getString("PROJECT_NAME")));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return result;
    }

    /**
     * Deprecated, too slow
     * Loads all projects from the database at once and returns them in a List
     * @return List <Project>
     */
    @SuppressWarnings("null")
	public List<Project> loadAllProjects() {

        List<Project> result = new ArrayList<>();
        Connection connection = null;
        int i = 0;

        if (connect()) {
            String query = "SELECT PROJECT_ID FROM Projects ORDER BY PROJECT_ID DESC LIMIT 1";
            try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    i++;
                    if(exists(i))
                        result.add(load(i));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return result;
    }


    /**
     * Saves a Project, all of its Timelines and all of its Events into the database
     * @param project
     * @return boolean
     */
    public boolean save(Project project) {

        if (connect()) {

            // delete existing project (overwrite)
            if (exists(project.ProjectID))
                delete(project);

            // Projects loop
            for (int i = 0; i < project.getTimelines().size(); i++) {

                Timeline tl = project.getTimelines().get(i);
                String projectsQuery = "INSERT INTO Projects " + "VALUES (" + project.ProjectID + ", " + project.getTimelines().get(i).timelineId + ", '" + project.projectName + "')";
                executeUpdate(projectsQuery);

                // extracting Date and Time from LocalDateTime
                Date startDate = Date.valueOf(tl.startDate);
                Date endDate = Date.valueOf(tl.endDate);

                if (tl.getEvents().size() < 1) {
                    String timelinesQuery = "INSERT INTO Timelines " + "VALUES (" + tl.timelineId + ", " + 0 + ", '" + startDate + "', '" + endDate + "', '" + tl.title + "')";
                    executeUpdate(timelinesQuery);
                } else {
                    // Timelines & Events loop
                    for (int j = 0; j < tl.getEvents().size(); j++) {


                        Event ev = tl.getEvents().get(j);

                        // extracting Date and Time from LocalDateTime
                        startDate = Date.valueOf(ev.startTime.toLocalDate());
                        Time startTime = Time.valueOf(ev.startTime.toLocalTime());

                        endDate = Date.valueOf("0000-01-01");
                        Time endTime = Time.valueOf("12:00:00");

                        if (ev.isDurationEvent()) {
                            endDate = Date.valueOf(((DurationEvent)ev).getEndTime().toLocalDate());
                            endTime = Time.valueOf(((DurationEvent)ev).getEndTime().toLocalTime());
                        }

                        int boolToInt = (ev.isDurationEvent) ? 1 : 0;
                        String imagePath= ev.imagepath.replace("\\", "\\\\");

                        // Saving Event
                        String eventsQuery = "INSERT INTO Events " + "VALUES (" + ev.eventid + ", '" + ev.title + "', '" + startTime + "', '" + endTime + "', '"
                                + startDate + "', '" + endDate + "', '" + ev.description + "', " + boolToInt + ", '" + ev.eventColor.toString() + "', '" + imagePath + "')";
                        executeUpdate(eventsQuery);

                        // Saving Timeline
                        String timelinesQuery = "INSERT INTO Timelines " + "VALUES (" + tl.timelineId + ", " + ev.eventid + ", '" + startDate + "', '" + endDate + "', '" + tl.title + "')";
                        executeUpdate(timelinesQuery);




                    }
                }
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return exists(project.ProjectID);
    }

    /**
     * Checks if a Project exists in the Database
     * @param ID
     * @return boolean
     */
    public boolean exists(int ID) {

        boolean result = false;

        if(connect()) {
            try {
                String query = "SELECT PROJECT_ID FROM Projects";
                Statement stmt = connection.createStatement();
                stmt.execute(query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    if (rs.getInt("PROJECT_ID") == ID)
                        result = true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return result;
    }

    /**
     * Deletes an Event
     * @param event
     * @return boolean
     */
    public boolean delete(Event event) {

        if (connect()) {
            query = "DELETE FROM Events WHERE EVENT_ID=" + event.eventid;
            executeUpdate(query);
            return true;
        } else {
            System.out.println("Connection to Database failed");
            return false;
        }
    }

    /**
     * Deletes a Timeline
     * @param timeline
     * @return boolean
     */
    public boolean delete(Timeline timeline) {

        if (connect()) {
            query = "DELETE FROM Timelines WHERE TIMELINE_ID=" + timeline.timelineId;
            executeUpdate(query);
            return true;
        } else {
            System.out.println("Connection to Database failed");
            return false;
        }
    }

    /**
     * Deletes a Project
     * @param project
     * @return boolean
     */
    public boolean delete(Project project) {

        if (connect()) {
            for (int i = 0; i < project.getTimelines().size(); i++) {
                delete(project.getTimelines().get(i));
                for (int j = 0; j < project.getTimelines().get(i).getEvents().size(); j++) {
                    delete(project.getTimelines().get(i).getEvents().get(j));
                }
            }
            query = "DELETE FROM Projects WHERE PROJECT_ID=" + project.ProjectID;
            executeUpdate(query);
            return true;
        } else {
            System.out.println("Connection to Database failed");
            return false;
        }
    }

    /**
     * Gets the highest ProjectID that is in the database
     * @return int
     */
    public int getHighestProjectID() {

        int highestID = 0;

        if (connect()) {
            String query = "SELECT PROJECT_ID FROM Projects ORDER BY PROJECT_ID DESC LIMIT 1";
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    highestID = rs.getInt("PROJECT_ID");
                }
                System.out.println("HIGHEST PROJECT ID: " + highestID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return highestID;
    }

    /**
     * Gets the highest TimelineID that is in the database
     * @return int
     */
    public int getHighestTimelineID() {

        int highestID = 0;

        if (connect()) {
            String query = "SELECT TIMELINE_ID FROM Timelines ORDER BY TIMELINE_ID DESC LIMIT 1";
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    highestID = rs.getInt("TIMELINE_ID");
                }
                System.out.println("HIGHEST TIMELINE ID: " + highestID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return highestID;
    }

    /**
     * Gets the highest EventID that is in the database
     * @return int
     */
    public int getHighestEventID() {

        int highestID = 0;

        if (connect()) {
            String query = "SELECT EVENT_ID FROM Events ORDER BY EVENT_ID DESC LIMIT 1";
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    highestID = rs.getInt("EVENT_ID");
                }
                System.out.println("HIGHEST EVENT ID: " + highestID);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return highestID;
    }

    /**
     * Deletes all content from the database
     * @return int
     */
    public boolean nukeDatabase() {

        boolean result = false;

        if(connect()) {
            try {
                query = "DELETE * FROM Projects; DELETE * FROM Events; DELETE * FROM Events";
                Statement stmt = connection.createStatement();
                @SuppressWarnings("unused")
                ResultSet rs = stmt.executeQuery(query);
                result = true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
        return result;
    }

    /**
     * Helper method, updates database with given query
     * @param query
     * @return int
     */
    private void executeUpdate(String query) {

        if (connect()) {
            try {
                stmt = connection.createStatement();
                stmt.executeUpdate(query);
                System.out.println(query + " executed.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("Connection to Database failed");
        }
    }

    /**
     * Helper method, infinite queries against the database, provokes connection refusal
     * @return int
     */
    public void stressTest() {

        String query = "SELECT * FROM Projects";
        int queryCount = 0;
        int i = 0;

        if(connect()) {
            try {
                if (connect()) {
                    while (i < 100000) {
                        executeUpdate(query);
                        queryCount++;
                        i++;
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Queries until connection refusal: " + queryCount);
            }
        } else {
            System.out.println("Connection to Database failed");
        }
    }

    /**
     * Helper method, establishes connection to the database, returns if it was successful
     * @return boolean
     */
    private boolean connect() {

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection!=null;
    }
}

