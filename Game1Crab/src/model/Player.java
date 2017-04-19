package model;

import java.awt.Rectangle;

public class Player extends Mover{
	
	public Player(Board b){ 
		this.yVel = 0;
		this.xVel = 0;
		this.location = new Rectangle(65, (b.height / 4), 85, 42); // Rectangle(int x, int y, int width, int height)
		this.yAcc_down = 1.6;
		this.yAcc_up = 2.5;
		this.board = b;
	}
	
	//when the jump button is pressed the crabs yVelocity is increased by a set amount
	public void jump(){	
		yVel = -29;
		yAcc_up = 2.5;
		yAcc_down = 1.6;
	}

}
