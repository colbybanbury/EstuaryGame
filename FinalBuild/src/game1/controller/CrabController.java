package game1.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

import game1.model.Board;
import game1.model.Player;
import game1.model.Enemy;
import game1.model.Friend;
import game1.view.CrabView;

public class CrabController implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	final int HEIGHT = (int)screenSize.getHeight();
	final int WIDTH = (int)screenSize.getWidth();
	
	public static Board board;
	public static Player player;
	public static CrabView view;
	public Random rand;
	private static Timer timer;
	private static Timer enemyTimer;
	private static Timer timer3;
	private static Timer friendTimer;
	private static Timer rectangleTimer;
	private static Timer droughtTimer;
	private static Timer stormTimer;
	private static Timer gracePeriodTimer;
	private static Timer questionBufferTimer;
	private final int DELAY = 70; //update rate = 70ms
	private final int DELAY2 = 1500; // update rate = 1.5s
	private final int DELAY3 = 25500; //update rate = 25.5s (waits until the scentTrail bricks fill screen)
   	private final int DELAY4 = 7; //update rate = 7ms
	private final int DELAY5 = 6000; // update rate = 5s
	private final int DELAY6 = 15000; // update rate = 15s
	private final int secDELAY = 1000; // update rate = 1s
	private final int questionBufferDELAY = 2250; // update rate 2.25 sec
   	private static boolean canBeAskedAQuestion;
   	private static boolean haventAddedDrought;
   	private static boolean haventAddedStorm;
   	private static boolean isItGracePeriod;
   	private static boolean isItQuestionBuffer;
   	private static boolean answer1Wrong;
   	private static boolean answer2Wrong;
   	private static boolean answer3Wrong;
   	private static int droughtStatus;
   	private static int stormStatus;
	private static int gracePeriodCounter;
	
	public CrabController(){
		CrabController.board = new Board(WIDTH, HEIGHT);//adjust values for size of board and length of path
		CrabController.view = new CrabView(WIDTH, HEIGHT);
		
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
		questionBufferTimer = new Timer(questionBufferDELAY, this);
		
		this.rand = new Random();
		
		CrabController.droughtStatus = 0;
		CrabController.stormStatus = 0;
		CrabController.gracePeriodCounter = 0;
		
		CrabController.canBeAskedAQuestion = false;
		CrabController.haventAddedDrought = true;
		CrabController.haventAddedStorm = true;
		CrabController.isItGracePeriod = false;
		CrabController.isItQuestionBuffer = false;
		CrabController.answer1Wrong = false;
		CrabController.answer2Wrong = false;
		CrabController.answer3Wrong = false;
	}


	public CrabController(String test){
		CrabController.board = new Board(WIDTH, HEIGHT);		

		questionBufferTimer = new Timer(questionBufferDELAY, this);
	}
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
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
		}else if(e.getSource() == questionBufferTimer){
			setIsItQuestionBuffer(false);
			questionBufferTimer.stop();
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
	
	public static Timer getQuestionBufferTimer(){
		return questionBufferTimer;
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
		if(board.questions.get(board.getCurrQuestion()).getAnswers().get(0) == board.questions.get(board.getCurrQuestion()).getCorrectAnswer()){
			gracePeriodCounter = 0;
			gracePeriodTimer.restart();
			isItGracePeriod = true;			

			setAnswer1Wrong(false);
			setAnswer2Wrong(false);
			setAnswer3Wrong(false);
			
			CrabView.jump.setEnabled(true);
			CrabView.jump.setVisible(true);
			
			CrabView.answer1.setEnabled(false);
			CrabView.answer1.setVisible(false);
			
			CrabView.answer2.setEnabled(false);
			CrabView.answer2.setVisible(false);
			
			CrabView.answer3.setEnabled(false);
			CrabView.answer3.setVisible(false);
		}else{
			setAnswer1Wrong(true);
		}
	}
	public static void answerButton2Press(){
		if(board.questions.get(board.getCurrQuestion()).getAnswers().get(1) == board.questions.get(board.getCurrQuestion()).getCorrectAnswer()){
			gracePeriodCounter = 0;
			gracePeriodTimer.restart();
			isItGracePeriod = true;			

			setAnswer1Wrong(false);
			setAnswer2Wrong(false);
			setAnswer3Wrong(false);
			
			CrabView.jump.setEnabled(true);
			CrabView.jump.setVisible(true);
			
			CrabView.answer1.setEnabled(false);
			CrabView.answer1.setVisible(false);
			
			CrabView.answer2.setEnabled(false);
			CrabView.answer2.setVisible(false);
			
			CrabView.answer3.setEnabled(false);
			CrabView.answer3.setVisible(false);
		}else{
			setAnswer2Wrong(true);
		}
	}
	public static void answerButton3Press(){
		if(board.questions.get(board.getCurrQuestion()).getAnswers().get(2) == board.questions.get(board.getCurrQuestion()).getCorrectAnswer()){
			gracePeriodCounter = 0;
			gracePeriodTimer.restart();
			isItGracePeriod = true;			

			setAnswer1Wrong(false);
			setAnswer2Wrong(false);
			setAnswer3Wrong(false);
			
			CrabView.jump.setEnabled(true);
			CrabView.jump.setVisible(true);
			
			CrabView.answer1.setEnabled(false);
			CrabView.answer1.setVisible(false);
			
			CrabView.answer2.setEnabled(false);
			CrabView.answer2.setVisible(false);
			
			CrabView.answer3.setEnabled(false);
			CrabView.answer3.setVisible(false);
		}else{
			setAnswer3Wrong(true);
		}
	}
	
	public static void returnToMainMenu(JFrame frame){
		CrabController.stopTimers();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	public static void replayGame(JFrame frame){
		CrabController.stopTimers();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

		timer.start();
		
		@SuppressWarnings("unused")
		CrabController c1 = new CrabController();
	}

	public static boolean getAnswer1Wrong() {
		return answer1Wrong;
	}

	public static void setAnswer1Wrong(boolean a1W) {
		CrabController.answer1Wrong = a1W;
	}
	
	public static boolean getAnswer2Wrong() {
		return answer2Wrong;
	}

	public static void setAnswer2Wrong(boolean a2W) {
		CrabController.answer2Wrong = a2W;
	}
	
	public static boolean getAnswer3Wrong() {
		return answer3Wrong;
	}

	public static void setAnswer3Wrong(boolean a3W) {
		CrabController.answer3Wrong = a3W;
	}

	public static boolean getIsItQuestionBuffer() {
		return isItQuestionBuffer;
	}

	public static void setIsItQuestionBuffer(boolean isItQuestionBuffer) {
		CrabController.isItQuestionBuffer = isItQuestionBuffer;
	}
	
	public static void stopTimers(){
		timer.restart();
		timer.stop();
		enemyTimer.restart();
		enemyTimer.stop();
		timer3.restart();
		timer3.stop();
		friendTimer.restart();
		friendTimer.stop();
		rectangleTimer.restart();
		rectangleTimer.stop();
		droughtTimer.restart();
		droughtTimer.stop();
		stormTimer.restart();
		stormTimer.stop();
		gracePeriodTimer.restart();
		gracePeriodTimer.stop();
		questionBufferTimer.restart();
		questionBufferTimer.stop();
	}
}
