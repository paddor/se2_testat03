package countlines;

import static org.junit.Assert.fail;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
JavaLinesTest.class,
SqlLinesTest.class,
CLinesTest.class,
SimpleLinesTest.class,
})
public class AllTests {
}
