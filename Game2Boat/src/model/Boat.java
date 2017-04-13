package model;

public class Boat {
	int xIncr, yIncr;
	int xLoc, yLoc;
	final int maxspeed = 150; // arbitrary value, please change
	int direction;
	int speed;
	int picNum;
	int frameCount;
	
	public void throttle() {} // please document all of these methods
	public void turn() {}
	public void generateWake() {}
	public int getXLoc() {return 0;}
	public int getYLoc() {return 0;}
	public int getDirection() {return 0;}
	public int getSpeed() {return 0;}
}
