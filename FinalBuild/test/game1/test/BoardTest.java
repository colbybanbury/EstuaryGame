package game1.test;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.util.List;

import org.junit.Test;
import game1.controller.CrabController;
import game1.model.Player;
import game1.model.Board;
import game1.model.Enemy;
import game1.model.Friend;

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
		CrabController c1 = new CrabController("test");
		@SuppressWarnings("static-access")
		Board b1 = c1.board;
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
		Board b1 = new Board(1000, 1200);
		Player p1 = b1.player;
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		p1.setXLoc(50);
		p1.setYLoc(350+e1.getImgHeight()-1);
		e1.setXLoc(50);
		e1.setYLoc(550);
		assertFalse(b1.checkCollision());
	}
	
	@Test
	public void droughtTest(){ //includes stopDraught, maybeAddDrought test
		Board b1 = new Board(1000, 1200);
		for (int i = 0; i < 600; i++){
			b1.setProgress(10);
			if (b1.getProgress() > 2*(1000-41)/5){
				assertTrue(b1.maybeAddDrought());
			}
			else{
				assertFalse(b1.maybeAddDrought());
			}
		}
		b1.drought();
		assertEquals(b1.scentTrailHeight, 300); // height * .75
		b1.stopDrought();
		assertEquals(b1.scentTrailHeight, 400);
	}
	
	@Test
	public void stormTest(){ //includes stopStorm() test
		Board b1 = new Board(1000, 1200);
		b1.storm();
		assertEquals(b1.wavyFactor, 4, 0.1);  // third argument is a delta, now required
		b1.stopStorm();
		assertEquals(b1.wavyFactor, 1, 0.1);
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
		b1.moverUpdate();
		assertEquals(b1.getProgress(), 0, 0.000);
	}
	
	@Test
	public void updateTest2(){ //player has started
		Board b1 = new Board(1000, 1200);
		b1.player.setStarted(true);
		b1.moverUpdate();
		assertEquals(b1.getProgress(), 0, 0.000);
	}
	

	@Test
	public void updateTest3(){ //player has started, enemy goes off screen, test removed
		Board b1 = new Board(1000, 1200);
		b1.player.setStarted(true);
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		e1.setXLoc(13 - e1.getImgWidth());
		b1.moverUpdate();
		assertTrue(b1.enemies.isEmpty());
	}
	
	@Test
	public void updateTest4(){ //player has started, friend goes off screen, test removed
		Board b1 = new Board(1000, 1200);
		b1.player.setStarted(true);
		Friend f1 = new Friend(b1);
		b1.friends.add(f1);
		f1.setXLoc(13 - f1.getImgWidth());
		b1.moverUpdate();
		assertTrue(b1.friends.isEmpty());
	}	

	@Test
	public void updateTest5(){ //player has started, collides with enemy
		Board b1 = new Board(1000, 1200);
		b1.player.setStarted(true);
		Enemy e1 = new Enemy(b1);
		b1.enemies.add(e1);
		b1.player.setXLoc(50);
		b1.player.setYLoc(350);
		e1.setXLoc(50+b1.player.getImgWidth()+18 - 1);
		e1.setYLoc(350);
		b1.moverUpdate();
		assertFalse(b1.checkCollision());
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
	
	@Test
	public void rectangleUpdate1(){
		Board b1 = new Board(1000, 1000);
		Player p1 = b1.player;
		List<Rectangle> l1 = b1.scentTrail;
		p1.setStarted(true);
		assertEquals(l1.size(), 1);
		b1.rectangleUpdate();
		assertEquals(l1.size(), 2);
	}

	@Test
	public void rectangleUpdate2(){
		Board b1 = new Board(1000, 1000);
		Player p1 = b1.player;
		List<Rectangle> l1 = b1.scentTrail;
		p1.setStarted(true);
		b1.drought();
		assertEquals(l1.size(), 1);
		b1.rectangleUpdate();
		assertEquals(l1.size(), 2);
		b1.stopDrought();
	}
	
	@Test
	public void rectangleUpdate3(){
		Board b1 = new Board(1000, 1000);
		Player p1 = b1.player;
		List<Rectangle> l1 = b1.scentTrail;
		p1.setStarted(true);
		b1.storm();
		assertEquals(l1.size(), 1);
		b1.rectangleUpdate();
		assertEquals(l1.size(), 2);
		b1.stopStorm();
	}
	
	@Test
	public void rectangleUpdate4(){
		Board b1 = new Board(1000, 1000);
		Player p1 = b1.player;
		List<Rectangle> l1 = b1.scentTrail;
		p1.setStarted(true);
		for (int i = 0; i < 3000; i++){
			b1.setProgress(10);
			if (b1.getProgress() > (b1.getWidth() - 41) / 2){
				assertTrue(b1.maybeAddStorm());
			}
			else{ assertFalse(b1.maybeAddStorm()); }
		}
		assertEquals(l1.size(), 1);
		b1.rectangleUpdate();
		assertEquals(l1.size(), 2);
	}
	
	@Test
	public void rectangleUpdate5(){
		Board b1 = new Board(1000, 1000);
		Player p1 = b1.player;
		List<Rectangle> l1 = b1.scentTrail;
		p1.setStarted(true);
		assertEquals(l1.size(), 1);
		for (int i = 1; i < 333; i++){
			assertEquals(i, l1.size());
			b1.rectangleUpdate();
		}
	}
}