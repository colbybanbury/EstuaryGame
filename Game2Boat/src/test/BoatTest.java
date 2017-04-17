package test;

import static org.junit.Assert.*;
import model.Boat;
import org.junit.Test;

public class BoatTest {

	int accel = -1;
	int mSpeed = 4;
	int speedInc = 1;
	int initialX = 50;
	int initialY = 50;
	int xLoc, speed;
	@Test
	public void throttleTest() {
		Boat b1 = new Boat(accel, speedInc, mSpeed, initialX, initialY);
		for (int i = 0; i < 8; i++){
			speed = b1.getSpeed();
			b1.throttle();
			System.out.println("xLoc: " + b1.getXLoc() + " Speed: " + b1.getSpeed() 
								+ " Accel: " + b1.getAcceleration());
			assertTrue((b1.getSpeed() == speed + speedInc) || b1.getSpeed() == b1.getMaxSpeed());
			assertTrue(b1.getSpeed() <= b1.getMaxSpeed());
		}
	}
	@Test
	public void moveTest(){
		Boat b1 = new Boat(accel, speedInc, mSpeed, initialX, initialY);
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		b1.move();
		assertEquals(speed + xLoc, b1.getXLoc());
		assertTrue((b1.getSpeed()== b1.getAcceleration() + speed) || (b1.getSpeed() == 0));
		b1.throttle();
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		b1.move();
		assertEquals(speed + xLoc, b1.getXLoc());
		assertTrue((b1.getSpeed()== b1.getAcceleration() + speed) || (b1.getSpeed() == 0));
	}
}
