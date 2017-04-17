package test;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	
	@Test
	public void test() {
		Player p = new player();
		assertEqual(p.getyAcc, -0.8);
		assertTrue(p.getLocation instanceof Rectangle)
	}
	
	@Test
	public void jumpTest(){
		Player p = new player();
		int yVel = p.getYVel();
		p.jump();
		assertTrue(yVel+5 == p.getYVel());
	}

}
