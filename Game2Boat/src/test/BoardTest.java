package test;

import model.Board;
import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void boardTest() {
		Board b1 = new Board();
		assertEquals(b1.getInnerDiameter(), b1.innerDiameter);
		assertEquals(b1.getOuterDiameter(), b1.outerDiameter);
	}

}
