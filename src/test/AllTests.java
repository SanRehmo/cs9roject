package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EventValidate.class, TimelineTest.class, TimelineValidate.class })
public class AllTests {

}
