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
	private Timer enemyTimer;
	private Timer timer3;
	private Timer friendTimer;
	private Timer rectangleTimer;
	private final int DELAY = 70; //update rate = 70ms
	private final int DELAY2 = 5000; // update rate = 5s
	private final int DELAY3 = 25500; //update rate = 24.5s (waits until the scentTrail bricks fill screen)
   	private final int DELAY4 = 35; //update rate = 35ms
	
	public CrabController(){
		this.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		this.view = new View(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		timer.start();
		
		enemyTimer = new Timer(DELAY2, this);
		
		timer3 = new Timer(DELAY3, this);
		
		friendTimer = new Timer(DELAY2, this);
		
		rectangleTimer = new Timer(DELAY4, this);
		rectangleTimer.setInitialDelay(15000);
	}
	
	public static void main(String[] args){
		CrabController crabController = new CrabController();
	}
	
	@Override
    public void actionPerformed(ActionEvent e){
		if(e.getSource() == timer){
			onTick();
			if(board.player.getStarted() && !enemyTimer.isRunning()){
				timer3.start();
				friendTimer.start();
				rectangleTimer.start();
			}
		}else if(e.getSource() == enemyTimer){
			if(board.player.getStarted() && !board.isAnswering){
				board.enemies.add(new Enemy(board));
			}
		}else if(e.getSource() == timer3){
			enemyTimer.start();
			friendTimer.stop();
			timer3.stop();
		}else if(e.getSource() == friendTimer){
			board.friends.add(new Friend(board));
		}else if(e.getSource() == rectangleTimer){
			onTick2();
		}
    }
	
	public void onTick(){
		board.moverUpdate();
		view.animate();
	}
	
	public void onTick2(){
		board.rectangleUpdate();
		view.animate();
	}
	
	
	public static void buttonPress(){
		if (!board.player.getStarted()){
			board.player.setStarted(true);
		}
		board.player.jump();
	}
}
