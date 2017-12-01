package game2.test;

import static org.junit.Assert.*;

import game2.model.Board;
import game2.model.Boat;
import game2.model.Estuary;

import org.junit.Test;

public class BoatTest {

	Board b = new Board(500,500, 300, 100);
	int xLoc, speed;
	@Test
	public void throttleTest() {
		System.out.println("throttleTest");
		System.out.println();
		Boat b1 = new Boat();
		b1.setSpeed(0);
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
		Boat b1 = new Boat();
		xLoc = b1.getXLoc();
		speed = b1.getSpeed();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		b1.move();
		System.out.println("xLoc: " + xLoc + " speed: " + speed);
		assertEquals(speed + xLoc, b1.getXLoc());
		assertEquals(b1.getSpeed(), 0);

		for (int i = 0; i < 5; i++){
			b1.throttle();
			System.out.println("xLoc: " + xLoc + " speed: " + speed);
			xLoc = b1.getXLoc();
			speed = b1.getSpeed();
			System.out.println("xLoc: " + xLoc + " speed: " + speed);
			b1.move();
			System.out.println("xLoc: " + xLoc + " speed: " + speed);
			assertEquals(speed + xLoc, b1.getXLoc());
			assertEquals(b1.getSpeed(), (int) (speed - b1.getDrag()*speed*speed));
		}
		System.out.println();
	}
	
	@Test
	public void turnLeftMoveTest(){
		double phi, radiusScale;
		System.out.println("turnLeftMoveTest: ");
		System.out.println();
		Boat b1 = new Boat();
		for (int i = 0; i < 15; i++){
			b1.setXLoc(0);
			b1.turnLeft(); // increase phi
			b1.throttle();
			speed = b1.getSpeed();
			phi = b1.getPhi();
			radiusScale  = b1.getRadiusScale();
			System.out.println("radiusScale: " + b1.getRadiusScale());
			b1.move();
			System.out.println("radiusScale: " + b1.getRadiusScale());
			if (b1.getRadiusScale() < 1.18){
				assertEquals(radiusScale + speed * Math.sin(phi) * Boat.RADIUS_SPEED_SCALER, 
						b1.getRadiusScale(), .1);
			}
			else{
				assertEquals(1.18, b1.getRadiusScale(), 0);
			}
		}
		
	}
	
	@Test
	public void turnRightMoveTest(){
		System.out.println("turnLeftMoveTest: ");
		System.out.println();
		Boat b1 = new Boat();
		b1.setXLoc(0);
		b1.turnRight(); // increase phi
		b1.throttle();
		int speed = b1.getSpeed();
		double phi = b1.getPhi();
		double radiusScale  = b1.getRadiusScale();
		for (int i = 0; i < 5; i++){
			b1.throttle();
			b1.turnRight();
			speed = b1.getSpeed();
			phi = b1.getPhi();
			radiusScale  = b1.getRadiusScale();
			System.out.println("radiusScale: " + b1.getRadiusScale());
			b1.move();
			System.out.println("radiusScale: " + b1.getRadiusScale());
			if (b1.getRadiusScale() > 0.82) {
				assertEquals(radiusScale + speed * Math.sin(phi) * Boat.RADIUS_SPEED_SCALER, 
						b1.getRadiusScale(), 0);
			}
			else {
				assertEquals(0.82, b1.getRadiusScale(), .1);
			}
		}
	}
	
	@Test
	public void turnTest(){
		Boat b1 = new Boat();
		double phi = b1.getPhi();
		for (int i = 0; i < 17; i++){
			b1.turnLeft();
			if (b1.getPhi() >= 2) assertEquals(2.0, b1.getPhi(), 0);
			else assertEquals(phi + .25, b1.getPhi(), 0);
			phi = b1.getPhi();
		}
		for (int i = 0; i < 17; i++){
			b1.turnRight();
			if (b1.getPhi() <= -2) assertEquals(-2.0, b1.getPhi(), 0);
			else assertEquals(phi - .25, b1.getPhi(), 0);
			phi = b1.getPhi();
		}
	}
	
	@Test
	public void wakeTest(){
		System.out.println("wakeTest");
		System.out.println();
		Boat b1 = new Boat();
		int threshold = b1.getThreshold();
		Estuary e1 = new Estuary(0);
		int damage = e1.getDamage();
		for (int i=0; i < 8; i++){
			b1.throttle();
			System.out.println(b1.getSpeed());
			if (b1.getSpeed() > threshold){
				damage+=(b1.getSpeed()- threshold)/Boat.DAMAGE_SCALE + 1;
				assertTrue(b1.generateWake(e1)!=0);
				System.out.println(((b1.getSpeed()- threshold)/Boat.DAMAGE_SCALE + 1) + " vs " + e1.getDamage());
				assertEquals(damage, e1.getDamage());
			}
			else {
				assertFalse(b1.generateWake(e1)!=0);
				assertEquals(damage, e1.getDamage());
			}
			damage = e1.getDamage();
		}
		System.out.println();
	}
}
