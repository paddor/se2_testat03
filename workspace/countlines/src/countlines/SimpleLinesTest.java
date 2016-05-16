package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SimpleLinesTest {

	@Before
	public void setUp() {
		LineCounter.countLines("fixtures/test1.txt");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(3, LineCounter.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(2, LineCounter.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(0, LineCounter.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(1, LineCounter.getEmptyLineCount());
	}
}