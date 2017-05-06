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
		assertEquals(f.getXVel(), -14, 0.000);
		assertEquals(f.getYVel(), 0, 0.000);
		assertEquals(f.getYAcc(), 0, 0.000);
		
	}
}
