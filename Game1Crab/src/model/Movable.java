package model;
import java.awt.Rectangle;

public interface Movable {
	public void setYLoc(int yLoc);
	public int getYLoc();
	public void setXLoc(int xLoc);
	public int getXLoc();
	public void setYVel(double yVel);
	public double getYVel();
	public double getXVel();
	public double getYAcc_up();
	public double getYAcc_down();
	public void update();
	
	public int getImgWidth();
	public int getImgHeight();
	public Rectangle getLocation();
}
