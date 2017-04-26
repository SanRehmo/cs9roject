package cs_9roject;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimelinesDAO {
	public TimelinesDAO (){
		
	}

    Connection connection = null;

    public Project load(int ID) {

		Statement stmt = null;
		Project result = new Project();
		Timeline timeline = null;
        ArrayList<Event> eventList = new ArrayList<Event>();

        try {
            connection = Database.establishConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (connection != null) {

			try {

                stmt = connection.createStatement();
                ResultSet rss = stmt.executeQuery("SELECT * FROM Events");

                while (rss.next()) {

                    int eventID = rss.getInt("EVENT_ID");
                    String eventTitle = rss.getString("TITLE");
                    LocalDateTime eventStart_time = LocalDateTime.of(rss.getDate("START_DATE").toLocalDate(), rss.getTime("START_TIME").toLocalTime());
                    LocalDateTime eventEnd_time = LocalDateTime.of(rss.getDate("END_DATE").toLocalDate(), rss.getTime("END_TIME").toLocalTime());
                    int eventImageID = rss.getInt("IMAGE_ID");
                    String eventDescription = rss.getString("DESCRIPTION");


                    Event event = new Event(eventID, eventTitle, eventStart_time, eventEnd_time, eventDescription, eventImageID);
                    eventList.add(event);
                }

				stmt = connection.createStatement();
                String query = "SELECT * FROM Timelines";
                ResultSet rs = stmt.executeQuery(query);
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

                    System.out.println(startDate);
                    System.out.println(endDate);

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

    public boolean isConnected() {
        return (connection != null);
    }
}

