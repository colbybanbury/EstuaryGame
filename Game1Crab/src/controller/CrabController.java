package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import model.Board;
import model.Mover;
import model.Player;
import model.Enemy;
import model.Friend;
import view.View;

public class CrabController {

	final int HEIGHT = 700;
	final int WIDTH = 1000;
	
	public static Board board;
	public static Player player;
	public View view;
	private Timer timer;

	public CrabController(){
		this.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		this.player = new Player(board);
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
		CrabController crabContoller = new CrabController();
	}
	
	public void onTick(){
		System.out.println("onTick() ran");
		player.update();
		view.animate();
		//TODO whatever the view needs
	}
	
	
	public static void buttonPress(){
		System.out.println("Controller knows button was pressed");
		player.jump();
	}
}
