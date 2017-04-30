package test;

import cs_9roject.Timeline;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimelineValidate {
	
	private static int count = 0;
	
	
	@Before
	public void setUp() throws Exception {	// Do before every test
		System.out.println("Run test method:" + (++count) + " ...");
	}

	@After
	public void tearDown() throws Exception {	// Do after every test
		System.out.println("Done with test " + count + "\n");
	}
	
	
	@Test
	public void timelineTitleLength() {
		Timeline timeline1 = new Timeline();
		timeline1.setTitle("This is a way too long title for this timeline");
		assertTrue(testLength(timeline1.getTitle()) > 25);
	}
	
	public int testLength(String title) {
		int length = 0;
		for (int i = 0; i < title.length(); i++){
			length = i;
		}
		return length;
	}
	

	@Test
	public void testSpecialCharacters() {
		Timeline timeline2 = new Timeline();
		timeline2.setTitle("12-Växjö_Småland");
		assertTrue("12-Växjö_Småland" == timeline2.getTitle());
	}
	
	
}
