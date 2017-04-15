package model;

public class Boat {
	private int xLoc = 0;
	private int speed;
	private int acceleration; //always negative, essentially drag
	private int speedInc; //How much speed is increased on button press
	
	private int maxSpeed; //changes based on the boat
	
	public Boat(int accel, int sInc, int mSpeed){
		this.acceleration = accel;
		this.speedInc = sInc;
		this.maxSpeed = mSpeed;
	}
	
	public void throttle(){	//called when button is pressed
		this.speed += speedInc;
		if(speed > maxSpeed)
			speed = maxSpeed;
	} 
	
	public void generateWake(){	//needs implementation not sure how this will work
		
	}
	
	public void move(){	//should be called every tick
		this.xLoc += speed;
		this.speed += acceleration;
		if(speed < 0)
			speed = 0;
	}
	
	//getters. Currently no setters but can be added if needed for testing
	public int getXLoc(){return this.xLoc;}
	public int getSpeed() {return this.speed;}
	public int getAcceleration(){return this.acceleration;}
	public int getMaxSpeed(){return this.maxSpeed;}
}
