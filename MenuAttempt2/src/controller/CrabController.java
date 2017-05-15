package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import model.CrabBoard;
import model.Mover;
import model.CrabPlayer;
import model.CrabEnemy;
import model.CrabFriend;
import view.CrabView;

public class CrabController  implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	final int HEIGHT = (int)screenSize.getHeight();
	final int WIDTH = (int)screenSize.getWidth();
	
	public static CrabBoard crabBoard;
	public static CrabPlayer crabPlayer;
	public static CrabView view;
	private static Timer timer;
	private Timer enemyTimer;
	private Timer timer3;
	private Timer friendTimer;
	private Timer rectangleTimer;
	private final int DELAY = 70; //update rate = 70ms
	private final int DELAY2 = 5000; // update rate = 5s
	private final int DELAY3 = 25500; //update rate = 24.5s (waits until the scentTrail bricks fill screen)
   	private final int DELAY4 = 35; //update rate = 35ms
   	private static boolean canBeAskedAQuestion = false;
	
	public CrabController(){
		CrabController.crabBoard = new CrabBoard(WIDTH, HEIGHT);//adjust values for size of board and length of path
		CrabController.view = new CrabView(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		timer.start();
		
		enemyTimer = new Timer(DELAY2, this);
		
		timer3 = new Timer(DELAY3, this);
		
		friendTimer = new Timer(DELAY2, this);
		
		rectangleTimer = new Timer(DELAY4, this);
		rectangleTimer.setInitialDelay(15000);
	}
	
	public static void main(String[] args){
		run();
	}
	
	public static void run(){
		CrabController crabController = new CrabController();
	}
	
	@Override
    public void actionPerformed(ActionEvent e){
		if(e.getSource() == timer){
			onTick();
			if(crabBoard.crabPlayer.getStarted() && !enemyTimer.isRunning()){
				timer3.start();
				friendTimer.start();
				rectangleTimer.start();
			}
		}else if(e.getSource() == enemyTimer){
			if(crabBoard.crabPlayer.getStarted()){
				crabBoard.crabEnemies.add(new CrabEnemy(crabBoard));
			}
		}else if(e.getSource() == timer3){
			enemyTimer.start();
			setCanBeAskedAQuestion(true);
			friendTimer.stop();
			timer3.stop();
		}else if(e.getSource() == friendTimer){
			crabBoard.crabFriends.add(new CrabFriend(crabBoard));
		}else if(e.getSource() == rectangleTimer){
			onTick2();
		}
    }
	
	public static void setCanBeAskedAQuestion(boolean bool){
		canBeAskedAQuestion = bool;
	}
	
	public static boolean getCanBeAskedAQuestion(){
		return canBeAskedAQuestion;
	}
	
	public static void onTick(){
		crabBoard.moverUpdate();
		view.animate();
	}
	
	public void onTick2(){
		crabBoard.rectangleUpdate();
		view.animate();
	}
	
	
	public static void buttonPress(){
		if (!crabBoard.crabPlayer.getStarted()){
			crabBoard.crabPlayer.setStarted(true);
		}
		crabBoard.crabPlayer.jump();
	}
}
