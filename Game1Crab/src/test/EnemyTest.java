package test;

import static org.junit.Assert.*;

import model.Board;
import model.Enemy;
import org.junit.Test;


public class EnemyTest {
	@Test
	public void test(){
		Board b = new Board(1000,1200);
		Enemy e = new Enemy(b);
		assertEquals(e.getXLoc(), 1000);
		assertTrue((e.getYLoc() >= 250) && (e.getYLoc() <= 750));
		assertEquals(e.getXVel(), -18, 0.000);
		assertEquals(e.getYVel(), 0, 0.000);
		assertEquals(e.getYAcc(), 0, 0.000);
	}
}
