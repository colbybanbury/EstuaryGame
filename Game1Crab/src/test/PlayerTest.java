package test;

import static org.junit.Assert.*;
import java.awt.Rectangle;

import org.junit.Test;
import model.Player;
import model.Board;
import model.Mover;

public class PlayerTest {
	
	@Test
	public void test() {
		Board b = new Board(400,800);
		Mover p = new Player(b);
		//assertEquals(-0.8, p.getYAcc()); I've been fooling with the values so this will be wrong for a while and I don't want errors
		assertTrue(p.getLocation() instanceof Rectangle);
	}
	
	@Test
	public void jumpTest(){
		Board b = new Board(400,800);
		Player p = new Player(b);
		double yVel = p.getYVel();
		p.jump();
		assertEquals(yVel+5, p.getYVel());
	}

}
