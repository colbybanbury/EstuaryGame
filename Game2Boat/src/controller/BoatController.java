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
	public static Estuary curEstuary;
	public View view;
	private Timer timer;
	private final int LAPLENGTH = 1000;
	private final int RADIUS = 300;
	
	
	public BoatController(){
		this.board = new Board(WIDTH, HEIGHT, RADIUS, LAPLENGTH);//adjust values for size of board and length of path
		this.boat = new Boat(-1, 10, 35, WIDTH/2, HEIGHT/2);//adjust values on acceleration, speedInc, and max speed
		this.game = new Game();
		this.curEstuary = board.getLapPath()[0];//starts at the first estuary
		this.view = new View(WIDTH, HEIGHT);
		
		for (int i = 0; i < 1000; i++) {
			onTick();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		BoatController boatControll = new BoatController();
	}
	
	public void onTick(){
		System.out.println("onTick() ran");
		boat.move();
		if(boat.getXLoc()>= board.getLapLength()){
			boat.setXLoc(0);
			game.setLap(game.getLap()+1);
			//TODO figure out how we want scoring to work
			//game.increaseScore();
		}
		game.decreaseTime();
		//TODO if time is up end game
		System.out.println("currently on estuary # " +(boat.getXLoc()*board.getEstuaryCount())/board.getLapLength());
		curEstuary = board.getLapPath()[(boat.getXLoc()*board.getEstuaryCount())/board.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		boat.generateWake(); //can return a boolean if it damages it if necessary
		view.animate();
		//TODO whatever the view needs
	}
	
	
	public static void buttonPress(){
		System.out.println("Controller knows button was pressed");
		boat.throttle();
	}

}
