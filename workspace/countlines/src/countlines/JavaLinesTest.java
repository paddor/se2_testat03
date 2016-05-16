package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JavaLinesTest {
	LineCounts lineCounts;

	@Before
	public void setUp() {
		lineCounts = LineCounter.countLines("fixtures/test1.java");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(275, lineCounts.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(238, lineCounts.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(10, lineCounts.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(27, lineCounts.getEmptyLineCount());
	}
}