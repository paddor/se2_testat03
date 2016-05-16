package countlines;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class JavaLinesTest {

	@Before
	public void setUp() {
		LineCounter.countLines("fixtures/test1.java");
	}


	@Test
	public void testTotalLineCount() {
		assertEquals(275, LineCounter.getTotalLineCount());
	}

	@Test
	public void testNetLineCount() {
		assertEquals(238, LineCounter.getNetLineCount());
	}

	@Test
	public void testCommentLineCount() {
		assertEquals(10, LineCounter.getCommentLineCount());
	}

	@Test
	public void testEmptyLineCount() {
		assertEquals(27, LineCounter.getEmptyLineCount());
	}
}