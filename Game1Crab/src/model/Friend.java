package model;

import java.awt.Rectangle;

public class Friend extends Mover{
	
	public Friend(Board b){ 
		this.yVel = 0;
		this.xVel = -18;
		this.yAcc = 0;
		//Friends start down in the bottom right corner and move across, carrying a fun fact along with them
		this.location = new Rectangle(b.width, (b.height - 114), 114, 64); // Rectangle(int x, int y, int width, int height)
		this.started = true;
		this.board = b;
	}
}
