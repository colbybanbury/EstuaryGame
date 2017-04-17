package test;

import static org.junit.Assert.*;
import model.Board;
import org.junit.Test;
import model.Player;
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
		//Setter needed to place player
		//Setter needed to place rectangles?
	}
	
	@Test
	public void checkCollisionTest1() { //Player same coords as Enemy
		Board b1 = new Board(400, 800);
		Player p1 = new Player(b1);
		p1.setXLoc(50);
		p1.setYLoc(50);
		Enemy e1 = new Enemy(b1);
		e1.setXLoc(50);
		e1.setYLoc(50);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	//TODO
	public void checkCollisionTest2() { //Right edge of Player meets Left edge of Enemy
		Board b1 = new Board(400, 800);
		Player p1 = new Player(b1);
		p1.setXLoc(50);
		p1.setYLoc(50);
		Enemy e1 = new Enemy(b1);
		e1.setXLoc(?);
		e1.setYLoc(?);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	//TODO
	public void checkCollisionTest3() { //Bottom of Player meets Top of Enemy
		Board b1 = new Board(400, 800);
		Player p1 = new Player(b1);
		p1.setXLoc(50);
		p1.setYLoc(50);
		Enemy e1 = new Enemy(b1);
		e1.setXLoc(?);
		e1.setYLoc(?);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	//TODO
	public void checkCollisionTest4() { //Top of Player meets Bottom of Enemy
		Board b1 = new Board(400, 800);
		Player p1 = new Player(b1);
		p1.setXLoc(50);
		p1.setYLoc(50);
		Enemy e1 = new Enemy(b1);
		e1.setXLoc(?);
		e1.setYLoc(?);
		assertTrue(b1.checkCollision());
	}
	
	@Test
	public void droughtTest(){
		Board b1 = new Board(400, 800);
		b1.drought();
		//TODO
		//Need getter for scent trail height
		assertEquals(b1.getScentTrailHeight(), 133); //Height/3/2 results in truncation, so 133
	}
	
	@Test
	public void stormTest(){
		Board b1 = new Board(400, 800);
		b1.storm();
		//TODO
		//Need getter for wavyFactor
		assertEquals(b1.getWavyFactor(), 10);
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
