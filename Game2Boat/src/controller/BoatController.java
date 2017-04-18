package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Board;
import model.Boat;
import model.Estuary;
import model.Game;
import view.View;

public class BoatController {
	final int HEIGHT = 1000;
	final int WIDTH = 1500;
	
	public static Board board;
	public static Boat boat;
	public Game game;
	public Estuary curEstuary;
	public View view;
	private Timer timer;
	
	public BoatController(){
		this.board = new Board(WIDTH, HEIGHT, 250, 400);//adjust values for size of board and length of path
		this.boat = new Boat(-1, 3, 7, WIDTH/2, HEIGHT/2);//adjust values on acceleration, speedInc, and max speed
		this.game = new Game();
		this.curEstuary = board.getLapPath()[0];//starts at the first estuary
		this.view = new View(WIDTH, HEIGHT);
		this.timer = new Timer(30, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onTick();
			}
		});
	}
	
	public static void main(String[] args){
		BoatController boatControll = new BoatController();
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
		//TODO if time is up end game
		curEstuary = board.getLapPath()[(boat.getXLoc()*board.getEstuaryCount())/board.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		boat.generateWake(curEstuary); //can return a boolean if it damages it if necessary
		view.repaint();
		//TODO whatever the view needs
	}
	
	public void setUp(){
		
		//TODO I'm sure there is more setup needed
	}
	
	public static void buttonPress(){
		System.out.println("Controller knows button was pressed");
		boat.throttle();
	}

}
