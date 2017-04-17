package test;

import static org.junit.Assert.*;
import model.Board;
import org.junit.Test;

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
	//TODO
	public void checkCollisionTest() {
		//Need setters and getters for enemy and player location
	}
	
	@Test
	public void droughtTest(){
		Board b2 = new Board(400, 800);
		b2.drought();
		//TODO
		//Need getter for scent trail height
		assertEquals(b2.getScentTrailHeight(), 133); //Height/3/2 results in truncation, so 133
	}
	
	@Test
	public void stormTest(){
		Board b3 = new Board(400, 800);
		b3.storm();
		//TODO
		//Need getter for wavyFactor
		assertEquals(b3.getWavyFactor(), 10);
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
