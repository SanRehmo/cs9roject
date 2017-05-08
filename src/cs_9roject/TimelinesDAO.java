package cs_9roject;


import javafx.scene.paint.Color;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimelinesDAO {

    private Connection connection = null;
    List<List<Event>> listOfEventLists = new ArrayList<List<Event>>();
    String query;

    // used to load a project from the DB through an ID
    public Project load(int ID) {

        Statement stmt = null;
        Project result = new Project(ID);
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

                // empty


                while (rss.next()) {


                    int eventID = rss.getInt("EVENT_ID");
                    String eventTitle = rss.getString("TITLE");
                    LocalDateTime eventStart_time = LocalDateTime.of(rss.getDate("START_DATE").toLocalDate(), rss.getTime("START_TIME").toLocalTime());
                    LocalDateTime eventEnd_time = LocalDateTime.of(rss.getDate("END_DATE").toLocalDate(), rss.getTime("END_TIME").toLocalTime());
                    int eventImageID = rss.getInt("IMAGE_ID");
                    String eventDescription = rss.getString("DESCRIPTION");
                    int timelineID = rss.getInt("TIMELINE_ID");
                    boolean isDurationEvent = rss.getBoolean("DURATIONEVENT");
                    Color color = Color.valueOf(rss.getString("COLOR"));


                    if (isDurationEvent){
                    	Event event = new DurationEvent(timelineID, eventID, eventTitle, eventStart_time, eventEnd_time, eventDescription, eventImageID, color);
                        eventList.add(event);
                    }
                    else{
                    	Event event = new NonDurationEvent(timelineID, eventID, eventTitle, eventStart_time, eventEnd_time, eventDescription, eventImageID, color);
                        eventList.add(event);
                    }
                    
                }


                // load all timelines in the project
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT Timelines.TIMELINE_ID, Timelines.EVENT_ID, Timelines.START_DATE, Timelines.END_DATE, Timelines.TITLE"
                        + " FROM Timelines JOIN Projects ON Projects.TIMELINE_ID=Timelines.TIMELINE_ID WHERE PROJECT_ID=" + ID);

                int lastTimelineID = 0;

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

        int highestID = 0;

        if (connection != null) {
            String query = "SELECT PROJECT_ID FROM Projects ORDER BY PROJECT_ID DESC LIMIT 1";
            try {
                stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    highestID = rs.getInt("PROJECT_ID");
                }
                for (int i = 1; i <= highestID; i++) {
                    if (load(i) != null) {
                        System.out.println(load(i).ProjectID);
                        result.add(load(i));
                    }
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

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (isConnected()) {

            // delte existing project (overwrite)
            if (exists(project.ProjectID))
                delete(project);

            // Projects loop
            for (int i = 0; i < project.timelines.size(); i++) {

                Timeline tl = project.timelines.get(i);
                String projects = "INSERT INTO Projects " + "VALUES (" + project.ProjectID + ", " + project.timelines.get(i).timelineId + ", '" + project.toString() + "')";
                execute(projects);


                // extracting Date and Time from LocalDateTime
                Date startDate = Date.valueOf(tl.startDate);
                Date endDate = Date.valueOf(tl.endDate);

                if (tl.events.size() < 1) {
                    String timelines = "INSERT INTO Timelines " + "VALUES (" + tl.timelineId + ", " + null + ", '" + startDate + "', '" + endDate + "', '" + tl.title + "', " + tl.isOnlyYears + ")";
                    execute(timelines);
                } else {
                    // Timelines & Events loop
                    for (int j = 0; j < tl.events.size(); j++) {


                        Event ev = tl.events.get(j);

                        int boolToInt = (tl.isOnlyYears) ? 1 : 0;
                        String timelines = "INSERT INTO Timelines " + "VALUES (" + tl.timelineId + ", " + ev.eventid + ", '" + startDate + "', '" + endDate + "', '" + tl.title + "', " + boolToInt + ")";
                    execute(timelines);

                    // extracting Date and Time from LocalDateTime
                    startDate = Date.valueOf(ev.startTime.toLocalDate());
                    Time startTime = Time.valueOf(ev.startTime.toLocalTime());

                    endDate = Date.valueOf("0000-01-01");
                    Time endTime = Time.valueOf("12:00:00");

                    if (ev.isDurationEvent()) {
                    	System.out.print("ok");
                        endDate = Date.valueOf(ev.getEndTime().toLocalDate());
                        endTime = Time.valueOf(ev.getEndTime().toLocalTime());
                    }

                        boolToInt = (ev.isDurationEvent) ? 1 : 0;
                        String events = "INSERT INTO Events " + "VALUES (" + ev.eventid + ", '" + ev.title + "', '" + startTime + "', '" + endTime + "', '"
                                + startDate + "', '" + endDate + "', '" + ev.description + "', " + ev.imageid + ", " + boolToInt + ", '" + ev.eventColor.toString() + "')";
                    execute(events);

                }
                }
            }
        }
    }

    public boolean exists(int ID) {

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        boolean result = false;

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
        return result;
    }

    public boolean delete(Event event) {

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (isConnected()) {
            query = "DELETE FROM Events WHERE EVENT_ID=" + event.eventid;
            execute(query);
            query = "UPDATE Timelines SET EVENT_ID=NULL WHERE EVENT_ID=" + event.eventid;
            execute(query);
            return true;
        } else return false;
    }

    public boolean delete(Timeline timeline) {

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (isConnected()) {
            query = "DELETE FROM Timelines WHERE TIMELINE_ID=" + timeline.timelineId;
            execute(query);
            query = "UPDATE Projects SET TIMELINE_ID=NULL WHERE TIMELINE_ID=" + timeline.timelineId;
            execute(query);
            for (int i = 0; i < timeline.events.size(); i++) {
                delete(timeline.events.get(i));
            }
            return true;
        } else return false;
    }

    public boolean delete(Project project) {

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (isConnected()) {
            query = "DELETE FROM Projects WHERE PROJECT_ID=" + project.ProjectID;
            execute(query);
            for (int i = 0; i < project.timelines.size(); i++) {
                delete(project.timelines.get(i));
                for (int j = 0; j < project.timelines.get(i).events.size(); j++) {
                    delete(project.timelines.get(i).events.get(j));
                }
            }
            return true;
        } else return false;
    }

    public int getHighestProjectID() {

        int highestID = 0;

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (connection != null) {
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
            System.out.println("Failed to make connection");
        }
        return highestID;
    }

    public int getHighestTimelineID() {

        int highestID = 0;

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (connection != null) {
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
            System.out.println("Failed to make connection");
        }
        return highestID;
    }

    public int getHighestEventID() {

        int highestID = 0;

        try {
            connection = Database.establishConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (connection != null) {
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
            System.out.println("Failed to make connection");
        }
        return highestID;
    }

    // CAREFUL WITH THIS
    public boolean deleteAllProjects() {

        int count = 1;

        if (isConnected()) {
            while (load(count) != null) {
                delete(load(count));
                count++;
            }
        }
        return (count > 1);
    }

    public boolean modify(Event eventToModify, Event newEvent) {

        // secure integrity
        newEvent.eventid = eventToModify.eventid;

        if (isConnected()) {
            query = "DELETE FROM Events WHERE EVENT_ID=" + eventToModify.eventid;
            execute(query);
            query = "INSERT INTO Events VALUES (" + eventProperties(newEvent) + ")";
            execute(query);
            return true;
        } else return false;
    }

    public boolean modify(Timeline timelineToModify, Timeline newTimeline) {

        newTimeline.timelineId = timelineToModify.timelineId;
        int count = 1;

        if (isConnected()) {
            query = "DELETE FROM Timelines WHERE TIMELINE_ID=" + timelineToModify.timelineId;
            execute(query);
            while (newTimeline.events.get(count) != null) {
                query = "INSERT INTO Timelines VALUES (" + newTimeline.timelineId + ", " + newTimeline.events.get(count).eventid + ", "
                        + newTimeline.startDate + ", " + newTimeline.endDate + ", " + newTimeline.title + ")";
                execute(query);
                count++;
            }
        }
        return (count > 1);
    }

    public boolean modify(Project projectToModify, Project newProject) {

        newProject.ProjectID = projectToModify.ProjectID;
        int count = 1;

        if (isConnected()) {
            query = "DELETE FROM Projects WHERE PROJECT_ID=" + projectToModify.ProjectID;
            execute(query);
            while (newProject.timelines.get(count) != null) {
                query = "INSERT INTO Projects VALUES (" + newProject.ProjectID + ", " + newProject.timelines.get(count).timelineId + ")";
                execute(query);
                count++;
            }
        }
        return (count > 1);
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

    public String eventProperties(Event event) {

        return event.eventid + ", " + event.title + ", " + event.startTime + ", " + event.endTime + ", "
                + event.startDate + ", " + event.endDate + ", " + event.description + ", " + event.imageid;
    }

    public String timelineProperties(Timeline timeline) {

        return timeline.timelineId + ", " + timeline.startDate + ", " + timeline.endDate + ", " + timeline.title + ", " + timeline.events;
    }
}

