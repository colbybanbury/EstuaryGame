package test;

import static org.junit.Assert.*;
import model.Boat;
import org.junit.Test;

public class BoatTest {

	int accel = -1;
	int mSpeed = 2;
	int speedInc = 1;
	int xLoc, speed;
	@Test
	public void throttleTest() {
		Boat b1 = new Boat(accel, speedInc, mSpeed);
		for (int i = 0; i < 15; i++){
			speed = b1.getSpeed();
			b1.throttle();
			assertTrue((b1.getSpeed() == speed + speedInc) || b1.getSpeed() == b1.getMaxSpeed());
			assertTrue(b1.getSpeed() <= b1.getMaxSpeed());
		}
	}
	@Test
	public void moveTest(){
		Boat b1 = new Boat(accel, speedInc, mSpeed);
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		b1.move();
		assertEquals(speed + xLoc, b1.getXLoc());
		assertTrue((b1.getSpeed()== b1.getAcceleration() + speed) || (b1.getSpeed() == 0));
	}
}
