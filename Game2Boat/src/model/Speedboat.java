package model;

public class Speedboat extends Boat{
	int maxSpeed = 50;
	
	public Speedboat(Board board) {super(board);}
	@Override
	public int getMaxSpeed(){return maxSpeed;}

}
