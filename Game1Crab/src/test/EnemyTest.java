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
		//TODO: Due to randomization, can't test getYLoc() at the moment?
		assertTrue(e.getXVel() == -18);
		assertTrue(e.getYVel() == 0);
		assertTrue(e.getYAcc() == 0);
	}
}
