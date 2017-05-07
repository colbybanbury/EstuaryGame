package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Game;

public class GameTest {

	@Test
	public void scoreTest() {
		Game g1 = new Game();
		assertEquals((int)g1.getScore(), 0);
		g1.decreaseScore(1);
		assertEquals((int)g1.getScore(), 0); // check underflow
		g1.increaseScore(1);
		assertEquals((int)g1.getScore(), 1);
		g1.decreaseScore(1);
		assertEquals((int)g1.getScore(), 0);
	}
	@Test
	public void timeTest(){
		Game g1 = new Game();
		for (int i = 120; i >= 0; i--){
			assertEquals((int)g1.getTime(), i);
			g1.decreaseTime();
		}
		g1.decreaseTime();
		assertEquals((int)g1.getTime(), 0);  // check underflow
	}
	@Test
	public void lapTest(){
		Game g1 = new Game();
		g1.setLap(6);
		assertEquals(6,(int) g1.getLap());
	}

}
