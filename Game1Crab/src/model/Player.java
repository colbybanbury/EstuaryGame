package model;

public class Player extends Mover{
	final int jumpInc = 5; //this is how quickly the crab moves up after jumping
	
	public Player( int imgH, int imgW,int yL, int xL, int yV, int xV, int yA, Rectangle loc){
		this.imgHeight = imgH;
		this.imgWidth = imgW;
		this.yLoc = yL;
		this.xLoc = xL;
		this.yVel = yV;
		this.xVel = xV;
		this.yAcc = yA;
		this.location = loc;
	}
	
	//when the jump button is pressed the crabs yVelocity is increased by a set amount
	public void jump(){	
		this.yVel += jumpInc;
	}
	
}
