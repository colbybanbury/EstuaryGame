package controller;

import java.awt.event.ActionEvent;

import model.Board;
import model.Boat;
import model.Estuary;
import model.Game;
import view.View;

public class BoatController {
	public static Board board;
	public static Boat boat;
	public Game game;
	public Estuary curEstuary;
	public View view;
	
	public BoatController(){
		this.board = new Board(400, 400, 150, 400);//adjust values for size of board and length of path
		this.boat = new Boat(-1, 3, 6, 350, 200);//adjust values on acceleration, speedInc, and max speed
		this.game = new Game();
		this.curEstuary = board.getLapPath()[0];//starts at the first estuary
		this.view = new View(400, 400);
	}
	
	public void onTick(){
		boat.move();
		if(boat.getXLoc()>= board.getLapLength()){
			boat.setXLoc(0);
			game.setLap(game.getLap()+1);
			//TODO figure out how we want scoring to work
			//game.increaseScore();
		}
		game.decreaseTime();
		curEstuary = board.getLapPath()[(boat.getXLoc()*board.getEstuaryCount())/board.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		//TODO View.repaint() or whatever the view needs
	}
	
	public void setUp(){
		
		//TODO I'm sure there is more setup needed
	}
	
	public static void buttonPress(){
		boat.move();
	}

}
