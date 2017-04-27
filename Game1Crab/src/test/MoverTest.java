package test;

import static org.junit.Assert.*;
import model.Board;
import model.Friend;
import model.Mover;

import org.junit.Test;

public class MoverTest {

	@Test
	public void updateTest() {
		Board b = new Board(1000,1000);
		Mover m = new Friend(b);
		m.setStarted(false);
		int yLoc = m.getYLoc();
		int xLoc = m.getXLoc();
		double yVel = m.getYVel();
		double xVel = m.getXVel();
		double yAcc = m.getYAcc();
		m.update();
		// not started
		assertEquals(xLoc, m.getXLoc());
		assertEquals(yLoc, m.getYLoc());
		assertEquals(yVel, m.getYVel(), 0);
		assertEquals(xVel, m.getXVel(), 0);
		m.setStarted(true);
		m.update();
		// started
		assertEquals(xLoc + xVel, m.getXLoc(), 0);
		assertEquals(yVel + yAcc, m.getYAcc(), 0);
		assertEquals(yLoc + yVel + yAcc, m.getYLoc(), 0);
		assertEquals(xVel, m.getXVel(), 0);
	}

}
