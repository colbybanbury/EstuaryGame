package game1.test;

import static org.junit.Assert.*;

import game1.model.Board;
import game1.model.Enemy;
import org.junit.Test;


public class EnemyTest {
	@Test
	public void test(){
		Board b = new Board(1000,1200);
		Enemy e = new Enemy(b);
		assertEquals(e.getXLoc(), 1000);
		assertTrue((e.getYLoc() >= .25*1200) && (e.getYLoc() <= .75*1200));
		assertTrue(e.getXVel() < -13);
		assertTrue(e.getXVel() >= -23); 
		assertEquals(e.getYVel(), 0, 0.000);
		assertEquals(e.getYAcc(), 0, 0.000);
	}
}
