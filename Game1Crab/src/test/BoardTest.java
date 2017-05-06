package test;

import static org.junit.Assert.*;

import org.junit.Test;
import model.Player;
import model.Board;
import model.Enemy;
import model.Friend;

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
	public void updateTest1(){ //player hasn't started
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
		b1.moverUpdate();
		assertEquals(b1.getProgress(), 0, 0.000);
	}
	
	@Test
	public void updateTest2(){ //player has started
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
	}
	

	@Test
	public void updateTest3(){ //player has started, enemy goes off screen, test removed
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		e1.setXLoc(17 - e1.getImgWidth());
		b1.moverUpdate();
		assertTrue(b1.enemies.isEmpty());
	}
	
	@Test
	public void updateTest4(){ //player has started, friend goes off screen, test removed
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
		Friend f1 = new Friend(b1);
		b1.friends.add(f1);
		f1.setXLoc(17 - f1.getImgWidth());
		b1.moverUpdate();
		assertTrue(b1.friends.isEmpty());
	}
	

	@Test
	public void updateTest5(){ //player has started, collides with enemy
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
		p1.setStarted(true);
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		p1.setXLoc(50);
		p1.setYLoc(350);
		e1.setXLoc(50+p1.getImgWidth()+18 - 1);
		e1.setYLoc(350);
		b1.moverUpdate();
		assertFalse(p1.getStarted());
	}
	
	
	@Test
	public void updateTest6(){ //player has started, doesn't collide with enemy
		Board b1 = new Board(1000, 1200);
		Player p1 = new Player(b1);
		p1.setStarted(true);
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		p1.setXLoc(50);
		p1.setYLoc(350);
		e1.setXLoc(300);
		e1.setYLoc(350);
		b1.moverUpdate();
		assertTrue(p1.getStarted());
	}
}
