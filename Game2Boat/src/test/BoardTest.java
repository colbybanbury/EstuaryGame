package test;

import static org.junit.Assert.*;

import org.junit.Test;

import enums.POWER_UP;
import model.Board;
import model.Estuary;

public class BoardTest {
	int lapLength = 400;
	int height = 300;
	int width = 300;
	int radius = 100;
	
	@Test
	public void Boardtest() {
		Board b = new Board(height, width, lapLength, radius);
		assertTrue(b instanceof Board);
	}
	
	@Test
	public void getLapLengthtest() {
		Board b = new Board(height, width, lapLength, radius);
		assertEquals(b.getLapLength(), lapLength);
	}
	
	@Test
	public void getLapPathTest(){
		Board b = new Board(height, width, lapLength, radius);
		assertTrue(b.getLapPath() instanceof Estuary[]);
	}
	
	@Test
	public void getHeighttest() {
		Board b = new Board(height, width, lapLength, radius);
		assertEquals(b.getHeight(), height);
	}
	
	@Test
	public void getWidthTest(){
		Board b = new Board(height, width, lapLength, radius);
		assertEquals(b.getWidth(), width);
	}
	
	@Test
	public void getPowerUpsTest(){
		Board b = new Board(height, width, lapLength, radius);
		System.out.println(b.getPowerUps());
		assertEquals(b.getPowerUps()[0][0], POWER_UP.NONE);
	}
	
	@Test
	public void getLapDivisionsTest(){
		Board b = new Board(height, width, lapLength, radius);
		assertEquals(40, b.getLapDivisions());
	}	
	
	@Test
	public void getRadiusTest(){
		Board b = new Board(height, width, lapLength, radius);
		assertEquals(radius, b.getRadius());
	}
}
