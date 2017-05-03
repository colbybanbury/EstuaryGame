package test;

import static org.junit.Assert.*;
import model.Friend;
import org.junit.Test;
import model.Board;

public class FriendTest {
	@Test
	public void test(){
		Board b = new Board(1000,1200);
		Friend f = new Friend(b);
		assertEquals(f.getXLoc(), 1000);
		assertEquals(f.getYLoc(), 1086);
		//TODO: test yAcc, yVel, xVel
		assertTrue(f.getXVel() == -18);
		assertTrue(f.getYVel() == 0);
		assertTrue(f.getYAcc() == 0);
		
	}
}
