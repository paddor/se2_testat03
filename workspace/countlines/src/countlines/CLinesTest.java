package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CLinesTest {

	@Before
	public void setUp() {
		LineCounter.countLines("fixtures/test1.c");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(18, LineCounter.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(11, LineCounter.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(2, LineCounter.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(5, LineCounter.getEmptyLineCount());
	}
}