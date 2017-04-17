package model;
import java.awt.Rectangle;

public abstract class Mover implements Movable{
	public double yVel;
	public double xVel;
	public double yAcc;

	Rectangle location;

	public void setYLoc(int yLoc){
		location.setY(yLoc);
	}
	
	public int getYLoc(){
		return location.getY();
	}

	public void setXLoc(int xLoc){
		location.setX(xLoc);
	}

	public int getXLoc(){
		return location.getX();
	}
	
	public void setYVel(double yVel){
		this.yVel = yVel;
	}
	
	public double getYVel(){
		return this.yVel;
	}

	public double getXVel(){
		return this.xVel;
	}
	
	public double getYAcc(){
		return this.yAcc;
	}
	
	public void update(){
		location.setX(location.getX() + xVel);
		this.yVel += yAcc;
		location.setY(location.getY() + yVel);
	}
	
	public int getImgWidth(){
		return location.getWidth();
	}
	
	public int getImgHeight(){
		return location.getHeight();
	}
	
	public Rectangle getLocation(){
		return this.location;
	}
}
