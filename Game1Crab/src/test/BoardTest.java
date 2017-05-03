package test;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Player;
import model.Board;
import model.Enemy;

public class BoardTest {

	@Test
	public void test() {
		Board b1 = new Board(400, 800);
		assertEquals(b1.getWidth(), 400);
		assertEquals(b1.getHeight(), 800);
	}

	@Test
	//TODO
	public void checkSalinityTest() {
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
		p1.setXLoc(50);
		p1.setYLoc(50);
		//Setter needed to place rectangles?
	}
	
	@Test
	public void checkCollisionTest1() { //Player same coords as Enemy
		Board b1 = new Board(1000, 1200);
		Player p1 = b1.player;
		p1.setXLoc(350);
		p1.setYLoc(350);
		b1.enemies.add(new Enemy(b1));
		Enemy e1 = b1.enemies.get(0);
		e1.setXLoc(350);
		e1.setYLoc(350);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	//TODO: Replace values for different image heights/widths
	public void checkCollisionTest2() { //Right edge of Player meets Left edge of Enemy
		Board b1 = new Board(1000, 1200);
		Player p1 = b1.player;
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		p1.setXLoc(50);
		p1.setYLoc(350);
		e1.setXLoc(50+p1.getImgWidth() - 1);
		e1.setYLoc(350);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	//TODO: Replace values for different image heights/widths
	public void checkCollisionTest3() { //Bottom of Player meets Top of Enemy
		Board b1 = new Board(1000, 1200);
		Player p1 = b1.player;
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		p1.setXLoc(50);
		p1.setYLoc(350);
		e1.setXLoc(50);
		e1.setYLoc(350+p1.getImgHeight() - 1);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	public void checkCollisionTest4() { //Top of Player meets Bottom of Enemy
		Board b1 = new Board(1000, 1200);
		Player p1 = b1.player;
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		p1.setXLoc(50);
		p1.setYLoc(350+e1.getImgHeight()-1);
		e1.setXLoc(50);
		e1.setYLoc(350);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	public void checkCollisionTest5() {
		//TODO: make this test check that there are no collisions
	}
	
	@Test
	public void droughtTest(){
		Board b1 = new Board(1000, 1200);
		b1.drought();
		assertEquals(b1.scentTrailHeight, 200); //Height/3 results in truncation, so 133
												// it's actually height/2. Change this test
												// if you change the implementation
	}
	
	@Test
	public void stormTest(){
		Board b1 = new Board(1000, 1200);
		b1.storm();
		assertEquals(b1.wavyFactor, 10, 0.1);  // third argument is a delta, now required
	}
	
	@Test
	//TODO
	public void constructionTest(){
		//Not sure how to test (yet)
	}
	
	@Test
	//TODO
	public void updateTest(){
		
	}
	
}
