package model;

public class Player extends Mover{
	
	public Player(){
		this.imgHeight = 200; //Whatever we decide
		this.imgWidth = 200;
		this.yLoc = 300; //Middle of the screen
		this.xLoc = 10; //This will be constant for the player
		this.yVel = 0; //This is initially zero
		this.xVel = 0; //The player does not move left/right
		this.yAcc = -5; //This will definitely need to be tested and changed
	}
	
	void jump(){
		//Please discuss at scrum
	}
}
