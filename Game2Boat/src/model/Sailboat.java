package model;

public class Sailboat extends Boat{
	int maxSpeed = 30;
	
	public Sailboat(Board board) {super(board);}
	@Override
	public int getMaxSpeed(){return maxSpeed;}

}
