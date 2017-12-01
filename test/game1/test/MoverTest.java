package game1.test;

import static org.junit.Assert.*;
import game1.model.Board;
import game1.model.Friend;
import game1.model.Mover;
import game1.model.Player;

import org.junit.Test;

public class MoverTest {

	@Test
	public void updateTest1() {
		Board b = new Board(1000,1000);
		Mover m = new Friend(b);
		m.setStarted(false);
		assertFalse(m.getStarted());
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
		assertEquals(yVel + yAcc, m.getYVel(), 0);
		assertEquals(yLoc + yVel + yAcc, m.getYLoc(), 0);
		assertEquals(xVel, m.getXVel(), 0);
	}
	@Test
	public void updateTest2(){
		Board b = new Board(100,100);
		Mover m = new Player(b);
		m.setStarted(false);
		assertFalse(m.getStarted());
		int yLoc = m.getYLoc();
		int xLoc = m.getXLoc();
		double yVel = m.getYVel();
		double xVel = m.getXVel();
		m.update();
		// not started
		assertEquals(xLoc, m.getXLoc());
		assertEquals(yLoc, m.getYLoc());
		assertEquals(yVel, m.getYVel(), 0);
		assertEquals(xVel, m.getXVel(), 0);
		m.setStarted(true);
		m.update();
		System.out.println(m.getYLoc() + " " + m.getYVel() + " " + m.getYAcc());
		// started
		assertEquals(xLoc + xVel, m.getXLoc(), 0);
		assertEquals(0, m.getYVel(), 0);
		//assertEquals(yLoc + m.getYVel() + m.getYAcc(), m.getYLoc(), 0);
		assertEquals(xVel, m.getXVel(), 0);
		
		m.setYLoc(0);
		m.update();
	}
	
}
