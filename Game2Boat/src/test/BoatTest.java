package test;

import static org.junit.Assert.*;
import model.Boat;
import org.junit.Test;

public class BoatTest {

	@Test
	public void test() {
		int oldDir, oldYIncr, oldXIncr;
		Boat b1 = new Boat();
		assertEquals(b1.getXLoc(), b1.xLoc);
		assertEquals(b1.getYLoc(), b1.yLoc);
		assertEquals(b1.getSpeed(), Math.sqrt((b1.xIncr)**2 +
											  (b1.yIncr)**2));
		assertEquals(b1.getSpeed(), b1.speed);
		assertEquals(b1.getDirection(), b1.direction);
		
		for (int i = 0; i < 15; i++){
			b1.throttle();
			assertTrue(b1.maxspeed >= b1.getSpeed());
		}
		
		oldDir = b1.getDirection();
		oldYIncr = b1.yIncr;
		oldXIncr = b1.xIncr;
		b1.turn();
		assertNotSame(oldDir, b1.getDirection());
		assertNotSame(oldYIncr, b1.yIncr);
		assertNotSame(oldXIncr, b1.xIncr);
		
	}

}
