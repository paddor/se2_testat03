package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SimpleLinesTest {
	LineCounts lineCounts;

	@Before
	public void setUp() {
		lineCounts = LineCounter.countLines("fixtures/test1.txt");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(3, lineCounts.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(2, lineCounts.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(0, lineCounts.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(1, lineCounts.getEmptyLineCount());
	}
}