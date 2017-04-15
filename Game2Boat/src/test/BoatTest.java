package test;

import static org.junit.Assert.*;
import model.Boat;
import org.junit.Test;

public class BoatTest {

	int accel = 1;
	int mSpeed = 1;
	int speedInc = 1;
	int xLoc, speed;
	@Test
	public void throttleTest() {
		Boat b1 = new Boat(accel, speedInc, mSpeed);
		for (int i = 0; i < 15; i++){
			speed = b1.getSpeed();
			b1.throttle();
			assertEquals(b1.getSpeed(), speed + speedInc);
			assertTrue(b1.getSpeed() < b1.getMaxSpeed());
		}
	}
	@Test
	public void moveTest(){
		Boat b1 = new Boat(accel, speedInc, mSpeed);
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		b1.move();
		assertEquals(b1.getSpeed() + xLoc, b1.getXLoc());
		assertEquals(b1.getSpeed(), b1.getAcceleration() + speed);
	}
}
