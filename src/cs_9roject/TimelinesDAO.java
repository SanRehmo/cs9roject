package cs_9roject;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class TimelinesDAO {
	public TimelinesDAO (){
		
	}

	public Project load(int ID) {

		Connection connection = null;
		Statement stmt = null;
		Project result = new Project();
		Timeline timeline = null;

		try {
			connection = Database.establishConnection();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		if (connection != null) {
			String query = "SELECT * FROM Timelines";
			try {
				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				int count = 1;
				while (rs.next()) {
					// result.addTimeline(new Timeline(rs.getDate("START_TIME").toLocalDate(), rs.getDate("END_TIME").toLocalDate(), rs.getString("TITLE")));

					LocalDate start_time = rs.getDate("START_TIME").toLocalDate();
					LocalDate end_time = rs.getDate("END_TIME").toLocalDate();
					String title = rs.getString("TITLE");

					timeline = new Timeline(start_time, end_time, title);
					result.addTimeline(timeline);
					// TESTING
					System.out.println(result.getTimeline(count));

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

	public void save(String path, Project timelines) {
		
	}

}
