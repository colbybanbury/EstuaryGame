package model;

import controller.BoatController;

/**
 * @author colby
 *
 */
public class Boat {
	private int xLoc = 0;
	private int speed;
	private int acceleration; //always negative, essentially drag
	private int speedInc; //How much speed is increased on button press
	
	private int maxSpeed; //changes based on the boat
	
	private double theta = 0.0;	//needed for circular representation in game
	private int boatCircleX;
	private int boatCircleY;
	
	private int initX;	//the intitial x and y values on the board
	private int initY;
	
	public Boat(int accel, int sInc, int mSpeed, int initialX, int initialY){
		this.acceleration = accel;
		this.speedInc = sInc;
		this.maxSpeed = mSpeed;
		this.initX = initialX;
		this.initY = initialY;
	}
	
	public void throttle(){	//called when button is pressed
		this.speed += speedInc;
		if(speed > maxSpeed)
			speed = maxSpeed;
	} 
	
	public void generateWake(){	//TODO needs implementation not sure how this will work
		
	}
	
	public void move(){	//should be called every tick
		this.xLoc += speed;
		this.theta = 2*Math.PI*(this.xLoc / BoatController.board.getLapLength());
		this.boatCircleX = initX + (int) (BoatController.board.getRadius() * Math.cos(theta));
		this.boatCircleY = initY + (int) (BoatController.board.getRadius() * Math.sin(theta));
		this.speed += acceleration;
		if(speed < 0)
			speed = 0;
	}
	
	//getters. Currently no setters but can be added if needed for testing
	public int getXLoc(){return this.xLoc;}
	public void setXLoc(int loc){this.xLoc = loc;}
	public int getSpeed() {return this.speed;}
	public int getAcceleration(){return this.acceleration;}
	public int getMaxSpeed(){return this.maxSpeed;}

	public int getBoatCircleX() {return boatCircleX;}

	public int getBoatCircleY() {return boatCircleY;}
	
	
}
