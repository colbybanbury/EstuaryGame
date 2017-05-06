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
		assertEquals(f.friendCounter, 0);
		
	}
	
	@Test
	public void friendCounterTest1(){ //friendCounter == 0
		Board b = new Board(1000, 1200);
		Friend f = new Friend(b);
		f.setFriendCounter(0);
		assertEquals(f.friendCounter, 0);
		assertEquals(f.getFriendCounter(), f.friendCounter);
	}
	
	@Test
	public void friendCounterTest2(){ //friendCounter > 0
		Board b = new Board(1000, 1200);
		Friend f = new Friend(b);
		f.setFriendCounter(10);
		assertEquals(f.friendCounter, 10);
		assertEquals(f.getFriendCounter(), f.friendCounter);
	}
	
	@Test
	public void friendCounterTest3(){ //friendCounter < 0
		Board b = new Board(1000, 1200);
		Friend f = new Friend(b);
		f.setFriendCounter(-10);
		assertEquals(f.friendCounter, -10);
		assertEquals(f.getFriendCounter(), f.friendCounter);
	}
}
