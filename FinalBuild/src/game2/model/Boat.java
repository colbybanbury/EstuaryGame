package game2.model;

import java.awt.Point;

import game2.controller.BoatController;

/**
 * @author colby
 * 
 * This class represents the boat the player controls. 
 * The boat's position is determined by an xLoc and a radiusScale which allows it's position to be independent of the size of the screen.
 * The boat can be throttled, turned right and left, moved, and it can generate wake.
 * The class keeps track of the boats xLoc, radiusScale, speed, and angle along with a number of attributes that control those 3 main attributes.
 *
 */
public class Boat {
	private int xLoc = 0;
	private int speed = 0;
	private double drag = 0.002; //always negative, essentially drag
	private int speedInc = 25; //How much speed is increased on button press
	
	private int maxSpeed = 400; //changes based on the boat
	
	private double phi = 0.0;//angle of the boat
	private double radiusScale = 1.0;
	public static final double RADIUS_SPEED_SCALER = .001;
	public static final int DAMAGE_SCALE = 50;
	
	private int threshold = 2*speedInc;
	
	public Boat(){}
	
	
	/**
	 * The throttle() method increases the speed by speedInc. If the speed exceeds the maxSpeed it is reset to maxSpeed.
	 *
	 */
	public void throttle(){	//called when button is pressed
		this.speed += speedInc;
		if(speed > maxSpeed)
			speed = maxSpeed;
		System.out.println("boat was throttled");
	} 
	
	/**
	 * The turnRight() method subtracts 0.25 from Phi(the angle of the boat). Phi is limited to be more than -2.0.
	 * 
	 */
	public void turnRight(){//turns the boat right and changes move()
		this.phi -= 0.25;
		if(this.phi<-2.0)
			this.phi = -2.0;
	}
	
	/**
	 * The turnLeft() method adds 0.25 from Phi(the angle of the boat). Phi is limited to be less than 2.0.
	 * 
	 * @return void
	 *
	 */
	public void turnLeft(){//turns the boat left and changes move()
		this.phi += 0.25;
		if(this.phi>2.0)
			this.phi = 2.0;
	}
	

	/**
	 * The generateWake() method checks to see if the boat speed is above the threshold
	 * and if it is it damages the current estuary based on the current speed and the DAMAGE_SCALE.
	 * 
	 * @param curEstuary The estuary that the boat is currently next to.
	 * 
	 * @return if the speed is above the threshold it returns true otherwise it returns false.
	 *
	 */
	 
	public boolean generateWake(Estuary curEstuary){
		System.out.println(this.getSpeed() + " > ?" + threshold);
		if (this.getSpeed() > threshold){
			System.out.println("generated Wake");
			//damage scales based on how much you are above the threshold
			curEstuary.damage((this.getSpeed()- threshold)/DAMAGE_SCALE + 1);
			return true;
		}
		return false;
	}
	
	/**
	 * the move method changes xLoc and radiusScale based on the speed and the angle of the boat.
	 * If the radiusScale is greater than 1.2 (too far from center) the radiusScale is set to 1.18 and 0.2 is subtracted from phi(straighten out the boat).
	 * If the radiusScale is less than 0.8 (too close to the center) the radiusScale is set to 0.82 and 0.2 is added to phi(straighten out the boat).
	 * The Speed is decreased by the square of the speed multiplied by a drag scaling variable
	 * 
	 */
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
		this.speed -= drag *speed*speed;
	}
	
	//getters. Currently no setters but can be added if needed for testing
	public int getXLoc(){return this.xLoc;}
	public void setXLoc(int loc){this.xLoc = loc;}
	public int getSpeed() {return this.speed;}
	public void setSpeed(int speed) {this.speed = speed;}

	public int getSpeedInc() {return this.speedInc;}
	public double getDrag(){return this.drag;}
	public int getMaxSpeed(){return this.maxSpeed;}
	
	public int getThreshold(){return threshold;}
	public double getPhi(){return phi;}
	public double getRadiusScale(){return radiusScale;}
	
	
}
