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
	private double drag = 0.002; //always negative, essentially drag
	private int speedInc = 25; //How much speed is increased on button press
	
	private int maxSpeed = 400; //changes based on the boat
	
	private double phi = 0.0; //TODO reset to 0 to start
	private double radiusScale = 1.0;
	private final double RADIUS_SPEED_SCALER = .001;//TODO find the right value for this
	
	private double theta = 0.0;	//needed for circular representation in game
	private double boatCircleX;
	private double boatCircleY;
	
	private int centerX;	//center of the board, boat rotates around
	private int centerY;
	
	private int threshold = 2*speedInc;
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
	
	public void turnRight(){//turns the boat right and changes move()
		this.phi -= 0.25;//TODO figure out good levels for actualy gameplay
		if(this.phi<-2.0)
			this.phi = -2.0;
	}
	
	public void turnLeft(){//turns the boat left and changes move()
		this.phi += 0.25;
		if(this.phi>2.0)
			this.phi = 2.0;
	}
	
	public boolean generateWake(Estuary curEstuary){
		System.out.println(this.getSpeed() + " > ?" + threshold);
		if (this.getSpeed() > threshold){
			System.out.println("generated Wake");
			//damage scales based on how much you are above the threshold
			curEstuary.damage(this.getSpeed()- threshold);
			return true;
		}
		return false;
	}
	
	public void move(){	//should be called every tick
		System.out.println("move called");
		this.xLoc += speed * Math.cos(phi);
		this.radiusScale += speed * Math.sin(phi) * RADIUS_SPEED_SCALER;
		if(this.radiusScale>1.2){
			this.radiusScale = 1.18;
			this.phi -= 0.2;
			}
		else if(this.radiusScale<0.8){
			this.radiusScale = 0.82;
			this.phi += 0.2;
			}
		updateCircleLoc();
		this.speed -= drag *speed*speed;
	}
	
	private void updateCircleLoc(){
		this.theta = (2*Math.PI*this.xLoc) / board.getLapLength();
		System.out.println((2*Math.PI*this.xLoc));
		System.out.println("Theta: " + this.theta);
		this.boatCircleX = centerX + ((board.getRadius()*this.radiusScale) * Math.cos(theta));
		this.boatCircleY = centerY + ((board.getRadius()*this.radiusScale) * Math.sin(theta));
		System.out.println("in boat x: " + this.boatCircleX + ", y: " + this.boatCircleY);
	}
	
	//getters. Currently no setters but can be added if needed for testing
	public int getXLoc(){return this.xLoc;}
	public void setXLoc(int loc){this.xLoc = loc;}
	public int getSpeed() {return this.speed;}
	public int getSpeedInc() {return this.speedInc;}
	public double getDrag(){return this.drag;}
	public int getMaxSpeed(){return this.maxSpeed;}

	public double getBoatCircleX() {return boatCircleX;}
	public double getBoatCircleY() {return boatCircleY;}
	public double getTheta(){return theta;}
	public int getThreshold(){return threshold;}
	public double getPhi(){return phi;}
	public double getRadiusScale(){return radiusScale;}
	
	
}
