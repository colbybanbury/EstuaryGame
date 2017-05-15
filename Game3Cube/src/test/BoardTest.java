package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Board;
import model.Cube;

public class BoardTest {

	@Test
	public void test() {
		Board b = new Board(1000,1000);
		b.shuffle(12);
		for (Cube c : b.getCubes()){
			assertTrue(c.getPicNum() < 12);
			assertEquals(c.getLocation().getX(), 10+ c.getCubeNum() * (b.getWidth()-20) / Board.NUM_CUBES, 0.01); 
			assertEquals(c.getLocation().getY(), b.getHeight() / 2, 0.01);
		}
	}

}
