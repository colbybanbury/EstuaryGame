package model;

import java.awt.Rectangle;

public class Friend extends Mover{
	public int friendCounter;
	public int textSize;
	
	public Friend(Board b){ 
		this.yVel = 0;
		this.xVel = -14;
		this.yAcc = 0;
		//Friends start down in the bottom right corner and move across, carrying a fun fact along with them
		this.location = new Rectangle(b.width, (b.height - 114), 114, 64); // Rectangle(int x, int y, int width, int height)
		this.started = true;
		this.board = b;
		
		this.friendCounter = b.friendCounter;
		
		b.friendCounter++;
	}
	
	public void setFriendCounter(int fc){
		this.friendCounter = fc;
	}

	public int getFriendCounter(){
		return this.friendCounter;
	}

	public void setTextSize(int ts){
		this.textSize = ts;
	}

	public int getTextSize(){
		return this.textSize;
	}
}
