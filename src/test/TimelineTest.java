package test;

import cs_9roject.Project;
import cs_9roject.Timeline;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TimelineTest {
	
	private static int count = 0;
	
	
	@Before
	public void setUp() throws Exception {	// Do before every test
		System.out.println("Run test method:" + (++count) + " ...");
	}

	@After
	public void tearDown() throws Exception {	// Do after every test
		System.out.println("Done with test " + count + "\n");
	}
	
	// Test U.1 timeline1
	
	
	
	@Test
	public void timelineIdTest() {
		List <Timeline> timelines = new ArrayList <Timeline>();
		for (int i=0; i<20; i++)
			timelines.add(new Timeline());
		for (int i=0; i<timelines.size(); i++)
			assertEquals(i, timelines.get(i).getTimelineId());
		}
	
	@Test
	public void timelineTest() {
		LocalDate date= LocalDate.of(2017, 01, 01);
		Timeline timeline1 = new Timeline();
		timeline1.setTimelineId(10000);
		timeline1.setStartTime(date);
		timeline1.setEndDate(date.plusDays(30));
		timeline1.setTitle("test");
		
		Timeline timeline2 = new Timeline(date,date.plusDays(30),"test" );
		assertEquals(timeline1.getTimelineId(), 10000);
		assertEquals(timeline1.getStartDate(), date);
		assertEquals(timeline2.getStartDate(), date);
		assertEquals(timeline1.getEndDate(), date.plusDays(30));
		assertEquals(timeline2.getEndDate(), date.plusDays(30));
		assertEquals(timeline1.getTitle(), "test");
		assertEquals(timeline2.getTitle(), "test");
		assertTrue(timeline1.equals(timeline2));
		
		}
	
	@Test
	public void timelineListTest() {
		Project timelines = timelinesBuilder(20);
		assertEquals(timelines.getTimelines().size(), 20);
		}

	private Project timelinesBuilder(int size) { // private method to support the test and generate timelines
		Project timelines = new Project();
		for (int i=0; i<size ; i++)
			timelines.addTimeline(new Timeline());
		return timelines;
	}
	
	
}
