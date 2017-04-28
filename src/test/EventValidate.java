package test;

import cs_9roject.Event;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EventValidate {
	
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
	public void eventTitleLength() {
		Event event1 = new Event(0, 0, null, null, null, null, 0);
		event1.setTitle("This is a way too long title for this timeline");
		assertTrue(testLength(event1.getTitle()) > 25);
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
		Event event2 = new Event(0, 0, null, null, null, null, 0);
		event2.setTitle("12-Växjö_Småland");
		assertTrue("12-Växjö_Småland" == event2.getTitle());
	}
	
	
}
