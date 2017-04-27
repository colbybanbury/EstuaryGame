package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
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

public class CrabController  implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	final int HEIGHT = (int)screenSize.getHeight();
	final int WIDTH = (int)screenSize.getWidth();
	
	public static Board board;
	public static Player player;
	public View view;
	private Timer timer;
	private Timer timer2;
	private Timer timer3;
	private final int DELAY = 70; //update rate = 70ms
	private final int DELAY2 = 3000; // update rate = 3s
	private final int DELAY3 = 24500; //update rate = 24.5s

	public CrabController(){
		this.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		this.view = new View(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		timer.start();
		
		timer2 = new Timer(DELAY2, this);
		timer2.start();
	}
	
	public static void main(String[] args){
		CrabController crabController = new CrabController();
	}
	
	@Override
    public void actionPerformed(ActionEvent e){
		if(e.getSource() == timer){
			onTick();
		}else if(e.getSource() == timer2){
			if(board.player.getStarted()){
				board.enemies.add(new Enemy(board));
			}
		}
    }
	
	public void onTick(){
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
