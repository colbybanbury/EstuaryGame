package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
		this.view = new View(WIDTH, HEIGHT);
	}
	
	public static void main(String[] args){
		CrabController crabController = new CrabController();
		
		for (int i = 0; i < 1000; i++) {
			crabController.onTick(i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void onTick(int tick){
		if (tick % 100 == 0 && board.player.getStarted()){
			board.enemies.add(new Enemy(board));
		}
		board.update();
		view.animate();
	}
	
	
	public static void buttonPress(){
		if (!board.player.getStarted()){
			board.player.setStarted(true);
		}
		board.player.jump();
	}
}
