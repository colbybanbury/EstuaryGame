package model;
import java.awt.Rectangle;

public abstract class Mover implements Movable{
	public int imgHeight;
	public int imgWidth;
	public int yLoc;
	public int xLoc;
	public int yVel;
	public int xVel;
	public int yAcc;

	Rectangle location;
	
	public void setYLoc(int yLoc){
		this.yLoc = yLoc;
	}
	
	public int getYLoc(){
		return this.yLoc;
	}
	
	public void setXLoc(int xLoc){
		this.xLoc = xLoc;
	}

	public int getXLoc(){
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
	
	public int getImgWidth(){
		return this.imgWidth;
	}
	
	public int getImgHeight(){
		return this.imgHeight;
	}
	
	public Rectangle getLocation(){
		return this.location;
	}
}
