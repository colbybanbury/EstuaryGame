package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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
	public static View view;
	public Random rand;
	private Timer timer;
	private Timer enemyTimer;
	private Timer timer3;
	private Timer friendTimer;
	private Timer rectangleTimer;
	private Timer droughtTimer;
	private Timer stormTimer;
	private static Timer gracePeriodTimer;
	private final int DELAY = 70; //update rate = 70ms
	private final int DELAY2 = 1500; // update rate = 1.5s
	private final int DELAY3 = 25500; //update rate = 25.5s (waits until the scentTrail bricks fill screen)
   	private final int DELAY4 = 7; //update rate = 7ms
	private final int DELAY5 = 6000; // update rate = 5s
	private final int DELAY6 = 15000; // update rate = 15s
	private final int secDELAY = 1000; // update rate = 1s
   	private static boolean canBeAskedAQuestion = false;
   	private static boolean haventAddedDrought = true;
   	private static boolean haventAddedStorm = true;
   	private static boolean isItGracePeriod = false;
   	private static int droughtStatus;
   	private static int stormStatus;
	private static int gracePeriodCounter;
	
	public CrabController(){
		CrabController.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		CrabController.view = new View(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		timer.start();
		
		enemyTimer = new Timer(DELAY2, this);
		
		timer3 = new Timer(DELAY3, this);
		
		rectangleTimer = new Timer(DELAY4, this);
		rectangleTimer.setInitialDelay(15000);
		
		friendTimer = new Timer(DELAY5, this);
		
		droughtTimer = new Timer(DELAY6, this);
		droughtTimer.setInitialDelay(30000);
		
		stormTimer = new Timer(DELAY6/2, this);
		stormTimer.setInitialDelay(15000);
		
		gracePeriodTimer = new Timer(secDELAY, this);
		
		this.rand = new Random();
		
		CrabController.droughtStatus = 0;
		CrabController.stormStatus = 0;
		CrabController.gracePeriodCounter = 0;
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
				droughtTimer.start();
			}
		}else if(e.getSource() == enemyTimer){
			if(board.player.getStarted()){
				int doSpawn = rand.nextInt(5);
				if(doSpawn % 2 == 0){
					board.enemies.add(new Enemy(board));
				}
			}
		}else if(e.getSource() == timer3){
			enemyTimer.start();
			setCanBeAskedAQuestion(true);
			friendTimer.stop();
			timer3.stop();
		}else if(e.getSource() == friendTimer){
			board.friends.add(new Friend(board));
		}else if(e.getSource() == rectangleTimer){
			onTick2();
		}else if(e.getSource() == gracePeriodTimer){
			gracePeriodCounter++;
			if(gracePeriodCounter >= 3){
				gracePeriodTimer.stop();
				isItGracePeriod = false;
				board.player.setStarted(true);
			}
		}else if(e.getSource() == droughtTimer){
			if(board.maybeAddDrought() && haventAddedDrought){
				board.drought();
				haventAddedDrought = false;
				droughtStatus = 2;
			}
			
			if(!haventAddedDrought){
				droughtStatus--;
				if(droughtStatus <= 0){
					board.stopDrought();
					droughtTimer.stop();
					stormTimer.start();
				}
			}
		}else if(e.getSource() == stormTimer){
			if(board.maybeAddStorm() && haventAddedStorm){
				board.storm();
				haventAddedStorm = false;
				stormStatus = 6;
			}
			
			if(!haventAddedStorm){
				stormStatus--;
				if(stormStatus <= 0){
					board.stopStorm();
					stormTimer.stop();
				}
			}
		}
    }
	
	public static void setCanBeAskedAQuestion(boolean bool){
		canBeAskedAQuestion = bool;
	}
	
	public static boolean getCanBeAskedAQuestion(){
		return canBeAskedAQuestion;
	}
	
	public static int getDroughtStatus(){
		return droughtStatus;
	}
	
	public static int getStormStatus(){
		return stormStatus;
	}
	
	public static int getGracePeriodCounter(){
		return gracePeriodCounter;
	}
	
	public static boolean getIsItGracePeriod(){
		return isItGracePeriod;
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
			if(!canBeAskedAQuestion){
				board.friends.add(new Friend(board));
              				board.player.setStarted(true);
			}
		}
		board.player.jump();
	}
	
	public static void answerButton1Press(){		
		gracePeriodCounter = 0;
		gracePeriodTimer.restart();
		isItGracePeriod = true;
		
		View.jump.setEnabled(true);
		View.jump.setVisible(true);
		
		View.answer1.setEnabled(false);
		View.answer1.setVisible(false);
		
		View.answer2.setEnabled(false);
		View.answer2.setVisible(false);
		
		View.answer3.setEnabled(false);
		View.answer3.setVisible(false);
		
	}
	public static void answerButton2Press(){		
		gracePeriodCounter = 0;
		gracePeriodTimer.restart();
		isItGracePeriod = true;
		
		View.jump.setEnabled(true);
		View.jump.setVisible(true);
		
		View.answer1.setEnabled(false);
		View.answer1.setVisible(false);
		
		View.answer2.setEnabled(false);
		View.answer2.setVisible(false);
		
		View.answer3.setEnabled(false);
		View.answer3.setVisible(false);
	}
	public static void answerButton3Press(){
		gracePeriodCounter = 0;
		gracePeriodTimer.restart();
		isItGracePeriod = true;
		
		View.jump.setEnabled(true);
		View.jump.setVisible(true);
		
		View.answer1.setEnabled(false);
		View.answer1.setVisible(false);
		
		View.answer2.setEnabled(false);
		View.answer2.setVisible(false);
		
		View.answer3.setEnabled(false);
		View.answer3.setVisible(false);
	}
}
