package test;

import static org.junit.Assert.*;
import model.Board;
import org.junit.Test;

public class BoardTest {

	@Test
	public void test() {
		Board b1 = new Board(400, 800);
		assertEquals(b1.getWidth(), 400);
		assertEquals(b1.getHeight(), 800);
	}

}
