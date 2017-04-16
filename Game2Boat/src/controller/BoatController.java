package controller;

import model.Board;
import model.Boat;
import model.Estuary;
import model.Game;

public class BoatController {
	public Board board;
	public Boat boat;
	public Game game;
	public Estuary curEstuary;
	
	public BoatController(){
		this.board = new Board(400, 400, 400);//adjust values for size of board and length of path
		this.boat = new Boat(-1, 3, 6);//adjust values on acceleration, speedInc, and max speed
		this.game = new Game();
		this.curEstuary = board.getLapPath()[0];//starts at the first estuary
	}
	
	public void onTick(){
		boat.move();
		game.decreaseTime();
		curEstuary = board.getLapPath()[(boat.getXLoc()*board.getEstuaryCount())/board.getLapLength()];
		//finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		//View.repaint() or whatever the view needs
	}

}
