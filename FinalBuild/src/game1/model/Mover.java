package game1.model;
import java.awt.Point;
import java.awt.Rectangle;

import game1.model.Board;

public abstract class Mover implements Movable{
	public double yVel;
	public double xVel;
	public double yAcc;
	boolean started;

	public Rectangle location;
		
	public Board board;

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
	
	public double getYAcc(){
		return this.yAcc;
	}
	
	public void update(){		
		if (this.started){
			
			yVel += yAcc;
			
			if (location.getY() + location.getHeight()>= this.board.getHeight() - location.getHeight() - 85){
				if (yVel <= 0){
					location.setLocation((int) (location.getX() + xVel), (int) (location.getY() + yVel));
				}else{
					yVel = 0;
					location.setLocation((int) (location.getX() + xVel), (int) (board.getHeight() - location.getHeight() - 85));
				}
			}else{
				location.setLocation((int) (location.getX() + xVel), (int) (location.getY() + yVel));
			}
		}
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
	
	public void setStarted(boolean start){
		this.started = start;
	}
	
	public boolean getStarted(){
		return started;
	}
}
