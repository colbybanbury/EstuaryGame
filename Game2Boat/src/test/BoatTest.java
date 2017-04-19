package test;

import static org.junit.Assert.*;

import model.Board;
import model.Boat;

import org.junit.Test;

public class BoatTest {

	Board b = new Board(500,500, 300, 100);
	int xLoc, speed;
	@Test
	public void throttleTest() {
		Boat b1 = new 
				Boat(b);
		for (int i = 0; i < 8; i++){
			speed = b1.getSpeed();
			b1.throttle();
			System.out.println("xLoc: " + b1.getXLoc() + " Speed: " + b1.getSpeed() 
								+ " drag: " + b1.getDrag());
			assertTrue(b1.getSpeed() == speed + b1.getSpeedInc() || b1.getSpeed() == b1.getMaxSpeed());
			assertTrue(b1.getSpeed() <= b1.getMaxSpeed());
		}
	}
	@Test
	public void moveTest(){
		Boat b1 = new Boat(b);
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		b1.move();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		assertEquals(speed + xLoc, b1.getXLoc());
		assertTrue(b1.getSpeed() == 0);

		b1.throttle();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		b1.move();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		assertEquals(speed + xLoc, b1.getXLoc());
		assertTrue(b1.getSpeed()== speed - b1.getDrag());
	}
	@Test
	public void wakeTest(){
		Boat b1 = new Boat(b);
		int threshold = b1.getThreshold();
		int speedInc = b1.getSpeedInc();
		for (int i=0; i < 8; i++){
			b1.throttle();
			System.out.println(b1.getSpeed());
			if (b1.getSpeed() > threshold){
				assertTrue(b1.generateWake());
			}
			else {
				assertFalse(b1.generateWake());
			}
		}
	}
	/*
	@Test
	public void updateCircleLocTest(){
		Boat b1 = new Boat(accel, speedInc, mSpeed, initialX, initialY);
		assertTrue(b1.getBoatCircleX() == initialX && b1.getBoatCircleY() == initialY);
		//b1.updateCircleLoc();
		//TODO hard to test since it uses data from the controller
	}
	*/
	
}
