package model;

import java.awt.Rectangle;

public class Player extends Mover{
	
	public Player(Board b){ 
		this.yVel = 0;
		this.xVel = 0;
		this.location = new Rectangle(65, (b.getHeight() / 2), 85, 42); // Rectangle(int x, int y, int width, int height)
		this.yAcc = 4.2;
		this.board = b;
		this.started = false;
		
	}
	
	//when the jump button is pressed the crabs yVelocity is increased by a set amount
	public void jump(){
		if (location.getY() >= 0){
			yVel = -30;
		}
	}

}
