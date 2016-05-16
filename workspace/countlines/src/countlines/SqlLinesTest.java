package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SqlLinesTest {
	LineCounts lineCounts;

	@Before
	public void setUp() {
		lineCounts = LineCounter.countLines("fixtures/test1.sql");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(10, lineCounts.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(5, lineCounts.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(2, lineCounts.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(3, lineCounts.getEmptyLineCount());
	}
}