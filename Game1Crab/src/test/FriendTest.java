package test;

import static org.junit.Assert.*;
import model.Friend;
import org.junit.Test;
import model.Board;

public class FriendTest {
	@Test
	public void test(){
		Board b = new Board(100,100);
		Friend f = new Friend(b);
	}
}
