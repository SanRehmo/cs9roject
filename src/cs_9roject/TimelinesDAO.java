package cs_9roject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimelinesDAO {
	public TimelinesDAO (){
		
	}


	public Project load(int ID) {

		Connection connection = null;
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

                    Event event = new Event(eventID, eventTitle, eventStart_time, eventEnd_time);
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

		Connection connection = null;
		Statement stmt;

		try {
			connection = Database.establishConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}


        for (int i = 0; i < project.timelines.size(); i++) {

			if (connection != null) {

                String update = "INSERT INTO Projects " + "VALUES (" + project.ProjectID + ", " + project.timelines.get(i) + ")";

				try {
					stmt = connection.createStatement();
					stmt.executeUpdate(update);
                    System.out.println("Project with the Project_ID: " + project.ProjectID + " and the Timeline_ID(s):" + project.timelines.get(i) + " has been saved!");
                } catch (Exception ex) {
                    ex.printStackTrace();
				}

			} else {
				System.out.println("Failed to make connection");
			}
		}
	}

}

