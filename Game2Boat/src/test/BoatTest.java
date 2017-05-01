package test;

import static org.junit.Assert.*;

import model.Board;
import model.Boat;
import model.Estuary;

import org.junit.Test;

public class BoatTest {

	Board b = new Board(500,500, 300, 100);
	int xLoc, speed;
	@Test
	public void throttleTest() {
		System.out.println("throttleTest");
		System.out.println();
		Boat b1 = new Boat(b);
		for (int i = 0; i < 17; i++){
			speed = b1.getSpeed();
			b1.throttle();
			System.out.println("xLoc: " + b1.getXLoc() + " Speed: " + b1.getSpeed() 
								+ " drag: " + b1.getDrag());
			assertTrue(b1.getSpeed() == speed + b1.getSpeedInc() || b1.getSpeed() == b1.getMaxSpeed());
			System.out.println("Second assert");
			assertTrue(b1.getSpeed() <= b1.getMaxSpeed());
		}
		System.out.println();
	}
	
	@Test
	public void moveTest(){
		System.out.println("moveTest");
		System.out.println();
		Boat b1 = new Boat(b);
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		b1.move();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		assertEquals(speed + xLoc, b1.getXLoc());
		assertTrue(b1.getSpeed() == 0);

		for (int i = 0; i < 5; i++){
			b1.throttle();
			System.out.println("xLoc: " + xLoc + " speed: " + speed);
			xLoc = b1.getXLoc();
			speed = b1.getSpeed();
			System.out.println("xLoc: " + xLoc + " speed: " + speed);
			b1.move();
			System.out.println("xLoc: " + xLoc + " speed: " + speed);
			assertEquals(speed + xLoc, b1.getXLoc());
			assertTrue(b1.getSpeed() == (int) (speed - b1.getDrag()*speed*speed));
		}
		System.out.println();
	}
	@Test
	public void wakeTest(){
		System.out.println("wakeTest");
		System.out.println();
		Boat b1 = new Boat(b);
		int threshold = b1.getThreshold();
		int speedInc = b1.getSpeedInc();
		Estuary e1 = new Estuary(0,0,0);
		int damage = e1.getDamage();
		for (int i=0; i < 8; i++){
			b1.throttle();
			System.out.println(b1.getSpeed());
			if (b1.getSpeed() > threshold){
				damage+=(b1.getSpeed()- threshold)/b1.DAMAGE_SCALE + 1;
				assertTrue(b1.generateWake(e1));
				System.out.println(((b1.getSpeed()- threshold)/b1.DAMAGE_SCALE + 1) + " vs " + e1.getDamage());
				assertEquals(damage, e1.getDamage());
			}
			else {
				assertFalse(b1.generateWake(e1));
				assertEquals(damage, e1.getDamage());
			}
			damage = e1.getDamage();
		}
		System.out.println();
	}
	
	
}
