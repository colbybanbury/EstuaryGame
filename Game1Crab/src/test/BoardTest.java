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
		
	}
	
	@Test
	//TODO
	public void checkCollisionTest() {
		
	}
	
	@Test
	public void droughtTest(){
		Board b2 = new Board(400, 800);
		b2.drought();
		assertEquals((((b2.getHeight())/3)/2), 133); //assertEquals on (((getHeight())/3)/2) due to truncation
	}
	
	@Test
	public void stormTest(){
		Board b3 = new Board(400, 800);
		b3.storm();
		assertEquals(b3.wavyFactor, 10);
	}
	
	@Test
	//TODO
	public void constructionTest(){
		
	}
	
	@Test
	//TODO
	public void updateTest(){
		
	}
	
}
