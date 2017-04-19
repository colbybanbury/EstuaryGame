package model;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Mover implements Movable{
	public double yVel;
	public double xVel;
	public double yAcc_up;
	public double yAcc_down;

	public Rectangle location;

	public void setYLoc(int yLoc){
		location.setLocation(new Point((int) location.getX(), yLoc));
	}
	
	public int getYLoc(){
		return (int) location.getY();
	}

	public void setXLoc(int xLoc){
		location.setLocation(new Point(xLoc, (int) location.getY()));
	}

	public int getXLoc(){
		return (int) location.getX();
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
	
	public double getYAcc_up(){
		return this.yAcc_up;
	}
	
	public double getYAcc_down(){
		return this.yAcc_down;
	}
	
	public void update(){
		if (yVel > 0){
			this.yVel += yAcc_down;
		}else{
			this.yVel += yAcc_up;
		}
		location.setLocation((int) (location.getX() + xVel), (int) (location.getY() + yVel));
	}
	
	public int getImgWidth(){
		return (int) location.getWidth();
	}
	
	public int getImgHeight(){
		return (int) location.getHeight();
	}
	
	public Rectangle getLocation(){
		return this.location;
	}
}
