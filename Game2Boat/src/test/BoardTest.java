package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Board;

public class BoardTest {
	int innerDiameter = 200;
	int outerDiameter = 200;
	int height = 300;
	int width = 300;
	
	@Test
	public void Boardtest() {
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		assertTrue(b instanceof Board);
	}
	
	@Test
	public void getInnerDiametertest() {
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		assertEquals(b.getInnerDiameter(), innerDiameter);
	}
	
	@Test
	public void getOuterDiameterTest(){
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		assertEquals(b.getOuterDiameter(), outerDiameter);
	}
	
	@Test
	public void getHeighttest() {
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		assertEquals(b.getHeight(), height);
	}
	
	@Test
	public void getWidthTest(){
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		assertEquals(b.getWidth(), width);
	}
	
	@Test
	public void estuariesTest(){
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		fail("Not yet implemented");
	}
	
	@Test
	public void boardArrayTest(){
		Board b = new Board(height, width, innerDiameter, outerDiameter);
		fail("Not yet implemented");
	}
	
	
}
