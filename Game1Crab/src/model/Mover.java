package model;
public abstract class Mover implements Movable{
	public int imgHeight;
	public int imgWidth;
	public int yLoc;
	public int xLoc;
	public int yVel;
	public int xVel;
	public int yAcc;
	
	public void setyLoc(int yLoc){
		this.yLoc = yLoc;
	}
	
	public int getyLoc(){
		return this.yLoc;
	}
	
	public void setxLoc(int xLoc){
		this.xLoc = xLoc;
	}

	public int getxLoc(){
		return this.xLoc;
	}
	
	public void setYVel(int yVel){
		this.yVel = yVel;
	}
	
	public int getYVel(){
		return this.yVel;
	}

	public int getXVel(){
		return this.xVel;
	}
	
	public int getYAcc(){
		return this.yAcc;
	}
	
	public void update(){
		this.xLoc += xVel;
		this.yVel += yAcc;
		this.yLoc += yVel;
	}
}
