package model;

import java.awt.Rectangle;

public class Player extends Mover{
	
	public Player(Board b){ 
		this.yVel = 0;
		this.xVel = 0;
		this.yAcc = -0.8;
		this.location = new Rectangle((b.width / 10), (b.height / 2), 243, 119); // Rectangle(int x, int y, int width, int height)
	}
	
	//when the jump button is pressed the crabs yVelocity is increased by a set amount
	public void jump(){	
		
	}

}
