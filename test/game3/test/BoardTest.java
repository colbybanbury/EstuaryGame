package game3.test;

import static org.junit.Assert.*;

import org.junit.Test;

import game3.model.Board;
import game3.model.Cube;

public class BoardTest {

	@Test
	public void test() {
		Board b = new Board(1000,1000);
		b.shuffle(12);
		for (Cube c : b.getCubes()){
			assertTrue(c.getPicNum() < 12);
			assertEquals(c.getLocation().getX(), 10+ c.getCubeNum() * (b.getWidth()-20) / Board.NUM_CUBES, 0.01); 
			assertEquals(c.getLocation().getY(), b.getHeight() *5/9, 0.01);
			c.changeLocation(1, 1);
			assertEquals(c.getLocation().getY(), 1, 0.01);
		}
	}

}
