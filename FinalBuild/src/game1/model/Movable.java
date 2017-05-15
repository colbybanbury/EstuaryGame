package game1.model;
import java.awt.Rectangle;

public interface Movable {
	public void setYLoc(int yLoc);
	public int getYLoc();
	public void setXLoc(int xLoc);
	public int getXLoc();
	public void setYVel(double yVel);
	public double getYVel();
	public double getXVel();
	public double getYAcc();
	public void update();
	
	public int getImgWidth();
	public int getImgHeight();
	public int getPicNum();
	public Rectangle getLocation();
}
