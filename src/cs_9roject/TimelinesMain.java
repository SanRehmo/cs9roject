package cs_9roject;

public class TimelinesMain {
    static Project timelines = new Project();
    static TimelinesDAO dao= new TimelinesDAO();

	public static void main(String[] args) {


        // Load project from database
        Project project = dao.load(3);

        // DB test for LOADING
        for (int i = 1; i < project.timelines.size(); i++) {

            Timeline timeline = project.getTimeline(i);

            // WORKS!
            System.out.println(timeline.getTitle());

            // WORKS!
            System.out.println(timeline.getEvent(i).getTitle());
            // System.out.println(timeline.getEvent(i).getDescription());
        }
    }

}
