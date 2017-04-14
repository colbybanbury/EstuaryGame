package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Board;
import model.Estuary;

public class BoardTest {
	int lapLength = 400;
	int height = 300;
	int width = 300;
	
	@Test
	public void Boardtest() {
		Board b = new Board(height, width, lapLength);
		assertTrue(b instanceof Board);
	}
	
	@Test
	public void getLapLengthtest() {
		Board b = new Board(height, width, lapLength);
		assertEquals(b.getLapLength(), lapLength);
	}
	
	@Test
	public void getLapPathTest(){
		Board b = new Board(height, width, lapLength);
		assertTrue(b.getLapPath() instanceof Estuary[]);
	}
	
	@Test
	public void getHeighttest() {
		Board b = new Board(height, width, lapLength);
		assertEquals(b.getHeight(), height);
	}
	
	@Test
	public void getWidthTest(){
		Board b = new Board(height, width, lapLength);
		assertEquals(b.getWidth(), width);
	}
	
	
	
}
