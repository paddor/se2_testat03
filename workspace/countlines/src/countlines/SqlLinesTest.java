package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SqlLinesTest {

	@Before
	public void setUp() {
		LineCounter.countLines("fixtures/test1.sql");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(10, LineCounter.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(5, LineCounter.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(2, LineCounter.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(3, LineCounter.getEmptyLineCount());
	}
}