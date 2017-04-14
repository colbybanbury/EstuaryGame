package model;

public interface Movable {
	public void setyLoc(int yLoc);
	public int getyLoc();
	public void setxLoc(int xLoc);
	public int getxLoc();
	public void setYVel(int yVel);
	public int getYVel();
	public int getXVel();
	public int getYAcc();
	public void update();
}
