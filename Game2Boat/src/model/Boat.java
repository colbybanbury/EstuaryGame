package model;

import java.awt.Point;

import controller.BoatController;

/**
 * @author colby
 *
 */
public abstract class Boat {
	private int xLoc = 0;
	private int speed = 0;
	private double acceleration = -0.6; //always negative, essentially drag
	private int speedInc = 5; //How much speed is increased on button press
	
	private int maxSpeed; //changes based on the boat
	
	private double theta = 0.0;	//needed for circular representation in game
	private double boatCircleX;
	private double boatCircleY;
	
	private int centerX;	//center of the board, boat rotates around
	private int centerY;
	
	private int threshold = 30;
	private Board board;
	
	public Boat(Board board){
		this.board = board;

		this.centerX = board.getWidth() / 2;
		this.centerY = board.getHeight() / 2;

		updateCircleLoc();
	}
	
	public void throttle(){	//called when button is pressed
		this.speed += speedInc;
		if(speed > maxSpeed)
			speed = maxSpeed;
		System.out.println("boat was throttled");
	} 
	
	public boolean generateWake(){
		if (this.getSpeed() >= threshold)
			System.out.println("Generate wake ///// NOT FINISHED");
		return this.getSpeed() >= threshold;
			//damage scales based on how much you are above the threshold
			//BoatController.curEstuary.damage(this.getSpeed()- (threshold -1));
	}
	
	public void move(){	//should be called every tick
		System.out.println("move called");
		this.xLoc += speed;
		updateCircleLoc();
		this.speed += acceleration *speed*speed;
		if(speed < 0){
			speed = 0;
		}
	}
	
	private void updateCircleLoc(){
		this.theta = (2*Math.PI*this.xLoc) / board.getLapLength();
		System.out.println((2*Math.PI*this.xLoc));
		System.out.println("Theta: " + this.theta);
		this.boatCircleX = centerX + (board.getRadius() * Math.cos(theta));
		this.boatCircleY = centerY + (board.getRadius() * Math.sin(theta));
		System.out.println("in boat x: " + this.boatCircleX + ", y: " + this.boatCircleY);
	}
	
	//getters. Currently no setters but can be added if needed for testing
	public int getXLoc(){return this.xLoc;}
	public void setXLoc(int loc){this.xLoc = loc;}
	public int getSpeed() {return this.speed;}
	public int getSpeedInc() {return this.speedInc;}
	public double getAcceleration(){return this.acceleration;}
	public int getMaxSpeed(){return 0;}

	public double getBoatCircleX() {return boatCircleX;}
	public double getBoatCircleY() {return boatCircleY;}
	public double getTheta(){return theta;}
	
	
}
