package cs_9roject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
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
                    LocalDate eventStart_time = rss.getDate("START_TIME").toLocalDate();
                    LocalDate eventEnd_time = rss.getDate("END_TIME").toLocalDate();
                    Event event = new Event(eventID, eventTitle, eventStart_time, eventEnd_time);
                    eventList.add(event);
                }

				stmt = connection.createStatement();
                String query = "SELECT * FROM Timelines";
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
					// result.addTimeline(new Timeline(rs.getDate("START_TIME").toLocalDate(), rs.getDate("END_TIME").toLocalDate(), rs.getString("TITLE")));

                    LocalDate timelineStart_time = rs.getDate("START_TIME").toLocalDate();
                    LocalDate timelineEnd_time = rs.getDate("END_TIME").toLocalDate();
                    String timelineTitle = rs.getString("TITLE");

                    timeline = new Timeline(timelineStart_time, timelineEnd_time, timelineTitle, eventList);
                    result.addTimeline(timeline);
					// TESTING

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

