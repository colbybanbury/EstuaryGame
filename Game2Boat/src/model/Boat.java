package model;

import java.awt.Point;

import controller.BoatController;

/**
 * @author colby
 *
 */
public class Boat {
	private int xLoc = 0;
	private int speed = 0;
	private double acceleration = -1.0; //always negative, essentially drag
	private int speedInc = 1; //How much speed is increased on button press
	
	private int maxSpeed = 4; //changes based on the boat
	
	private double theta = 0.0;	//needed for circular representation in game
	private double boatCircleX;
	private double boatCircleY;
	
	private int initX;	//the initial x and y values on the board
	private int initY;
	
	private int threshold = 30;
	private Board board;
	
	public Boat(Board board){
		this.board = board;
		this.initX = board.getWidth() / 2 + board.getRadius();
		this.initY = board.getHeight();
		updateCircleLoc();
	}
	
	public void throttle(){	//called when button is pressed
		this.speed += speedInc;
		if(speed > maxSpeed)
			speed = maxSpeed;
		System.out.println("boat was throttled");
	} 
	
	public boolean generateWake(){	//TODO throws a nullpointer but not sure why
		System.out.println("Generate wake ///// NOT FINISHED");
		return this.getSpeed() >= threshold;
			//damage scales based on how much you are above the threshold
			//BoatController.curEstuary.damage(this.getSpeed()- (threshold -1));
	}
	
	public void move(){	//should be called every tick
		System.out.println("move called");
		this.xLoc += speed;
		updateCircleLoc();
		this.speed += (acceleration / 10)*speed;
		if(speed < 0){
			speed = 0;
		}
	}
	
	private void updateCircleLoc(){
		this.theta = (2*Math.PI*this.xLoc) / board.getLapLength();
		System.out.println((2*Math.PI*this.xLoc));
		System.out.println("Theta: " + this.theta);
		this.boatCircleX = initX + (board.getRadius() * Math.cos(theta));
		this.boatCircleY = initY + (board.getRadius() * Math.sin(theta));
		System.out.println("in boat x: " + this.boatCircleX + ", y: " + this.boatCircleY);
	}
	
	//getters. Currently no setters but can be added if needed for testing
	public int getXLoc(){return this.xLoc;}
	public void setXLoc(int loc){this.xLoc = loc;}
	public int getSpeed() {return this.speed;}
	public int getSpeedInc() {return this.speedInc;}
	public double getAcceleration(){return this.acceleration;}
	public int getMaxSpeed(){return this.maxSpeed;}

	public double getBoatCircleX() {return boatCircleX;}
	public double getBoatCircleY() {return boatCircleY;}
	public double getTheta(){return theta;}
	
	
}
