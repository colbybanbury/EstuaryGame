package game1.model;

import java.awt.Rectangle;

public class Friend extends Mover{
	public int friendCounter;
	public int textSize;
	private int picNum = 0;
	
	public Friend(Board b){ 
		this.yVel = 0;
		this.xVel = -14;
		this.yAcc = 0;
		//Friends start down in the bottom right corner and move across, carrying a fun fact along with them
		this.location = new Rectangle(b.width, (b.height - 129), 114, 64); // Rectangle(int x, int y, int width, int height)
		this.started = true;
		this.board = b;
		
		this.friendCounter = b.friendCounter;
		
		b.friendCounter++;
	}
	
	@Override
	public void update(){
		super.update();
		picNum = ++picNum % 3;
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
	public int getPicNum(){
		return picNum;
	}
}
