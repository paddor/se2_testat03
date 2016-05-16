package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CLinesTest {
	LineCounts lineCounts;

	@Before
	public void setUp() {
		lineCounts = LineCounter.countLines("fixtures/test1.c");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(18, lineCounts.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(11, lineCounts.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(2, lineCounts.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(5, lineCounts.getEmptyLineCount());
	}
}