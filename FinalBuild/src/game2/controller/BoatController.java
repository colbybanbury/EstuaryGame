package game2.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.Timer;

import game2.enums.POWER_UP;
import game2.model.Board;
import game2.model.Boat;
import game2.model.Estuary;
import game2.model.Game;
import game2.view.BoatView;

/**
 * 
 * @author colby
 *
 *Controls the Boat Game
 */
public class BoatController implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	final int HEIGHT = (int)screenSize.getHeight() - 50;
	final int WIDTH = (int)screenSize.getWidth();
	
	private static Timer timer;
	private static Timer powerUpTimer;
	private static Timer secondTimer;
	private final int DELAY = 50;//update rate (50ms) may need to make faster
	private final int SECOND_DELAY = 1000;//1000ms  = 1s
	
	public static Board Boatboard;
	public static Boat boat;
	public static Game Boatgame;
	public static Estuary curEstuary;
	public static POWER_UP curPowerUp;
	public BoatView Boatview;
	private final int LAPLENGTH = 5000;
	private final int RADIUS = (HEIGHT>WIDTH)? 11* WIDTH / 32 : 11*HEIGHT / 32;
	Random random = new Random();
	private boolean spawn;//if a powerUp should spawn or be taken away
	public static boolean end; //is the time up
	int wait; //counter for how many powerUps are spawned before they are cleared
	
	

	public BoatController(){
		/**
		 * initializes the boat game and sets up all the timers
		 */
		spawn = true;
		end = false;
		wait = 0;
		BoatController.Boatboard = new Board(WIDTH, HEIGHT, LAPLENGTH, RADIUS);//adjust values for size of board and length of path
		BoatController.boat = new Boat();//adjust values on acceleration, speedInc, and max speed
		BoatController.Boatgame = new Game();
		BoatController.curEstuary = Boatboard.getLapPath()[0];//starts at the first estuary
		this.Boatview = new BoatView(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		powerUpTimer = new Timer(DELAY*50, this);
		secondTimer = new Timer(SECOND_DELAY, this);
		timer.start();
		powerUpTimer.start();
		secondTimer.start();
	}
	
	
	public static void main(String[] args){
		@SuppressWarnings("unused")
		BoatController boatController = new BoatController();
	}
	
	public void onTick(){
		/**
		 * onTick is called when the main timer ticks. It updates the model with move() and calls animate in the view
		 * It also calls CheckCollision()
		 */
		System.out.println("onTick() ran");
		boat.move();
		if(boat.getXLoc()>= Boatboard.getLapLength()){
			boat.setXLoc(0);
			Boatgame.setLap(Boatgame.getLap()+1);
			Boatgame.increaseScore(100);
		}
		System.out.println("currently on estuary # " +(boat.getXLoc()*Boatboard.getLapDivisions())/Boatboard.getLapLength());
		curEstuary = Boatboard.getLapPath()[(boat.getXLoc()*Boatboard.getLapDivisions())/Boatboard.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		Boatgame.increaseDamagePenalty(boat.generateWake(curEstuary)); //can return a boolean if it damages it if necessary
		Boatview.animate();
		
		checkCollision();
	}
	
	private void powerUpTick(boolean spawn){
		/*
		 * called by the powerUp timer on tick. spawns powerups in a random place
		 */
		if(spawn){
			int a = random.nextInt(Boatboard.getLapDivisions());
			int b = random.nextInt(3);
			System.out.println("spawning powerUp");
			if(Boatboard.getPowerUps()[a][b] != POWER_UP.ROCK){//don't replace rocks
				if(random.nextInt(5)==0)
					Boatboard.getPowerUps()[a][b] = POWER_UP.SEAGRASS;//happens less often than oysters
				else
					Boatboard.getPowerUps()[a][b] = POWER_UP.OYSTER;
			}
			
		}
		else{//clear powerups
			System.out.println("De-spawning powerUps");
			for(int i = 0; i<3; i++){
				for(int j = 0; j < Boatboard.getLapDivisions(); j++){
					if(Boatboard.getPowerUps()[j][i] != POWER_UP.ROCK)//but don't clear rocks
						Boatboard.getPowerUps()[j][i] = POWER_UP.NONE;
				}
			}
		}
		
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		/**
		 * handles the timers and has the end condition
		 */
		if(e.getSource() == timer){
			onTick();
		}
		if(e.getSource() == powerUpTimer){
			spawn = wait < 4;
			powerUpTick(spawn);
			wait = (wait +1) % 5;
		}
		if(e.getSource() == secondTimer){
			Boatgame.decreaseTime();
			if(Boatgame.getTime() <= 0){
				secondTimer.stop();
				end = true;
			}
		}
    }
	
    public static void keyPressed(KeyEvent e) {
    	/**
		 * handles that key presses the get passed up from the view
		 */
    	int key = e.getKeyCode();
    	if (key == KeyEvent.VK_LEFT) {
    		System.out.println("left key sensed");
    	    BoatController.boat.turnLeft();
    	}
    	if (key == KeyEvent.VK_RIGHT) {
    		System.out.println("right key sensed");
    	    BoatController.boat.turnRight();
    	}
    	if(key == KeyEvent.VK_SPACE){
    		System.out.println("space key sensed");
    		boat.throttle();
    	}
    }
    
    public void checkCollision(){
    	/**
		 * checks collisions with power ups.
		 * oysters spawn more gabions
		 * seaGrass repairs damage done to estuaries
		 * rocks repell the boat
		 */
    	int rowNum = (boat.getXLoc()*Boatboard.getLapDivisions())/Boatboard.getLapLength();
    	int columnNum = (int) ((boat.getRadiusScale() - 0.8)*7.5);
    	//gets what ever powerup is at the boats current location and if there is one there it does whatever it needs to
    	curPowerUp = Boatboard.getPowerUps()[rowNum][columnNum];
    	System.out.println("boat is at powerUP["+(boat.getXLoc()*Boatboard.getLapDivisions())/Boatboard.getLapLength()+"][" + (int) ((boat.getRadiusScale() - 0.8)*7.5) +"]" );
    	switch(curPowerUp){
    	case OYSTER:
    		Boatgame.increaseScore(20);
    		System.out.println("picked up an Oyster");
    		Boatboard.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates oyster power up
    		Estuary tempE;
    		for(int i = -1; i<=1; i++){ 
    			int temp = (i+(boat.getXLoc()*Boatboard.getLapDivisions())/Boatboard.getLapLength())%Boatboard.getLapDivisions();
    			if (temp <0){
    				temp += Boatboard.getLapDivisions();
    			}
    			tempE = Boatboard.getLapPath()[temp];
    			if(tempE.getType()!=3){//as long as the estuary isn't open water
    				tempE.setType(2);}// makes the current estuary and the two adjacent estuaries have gabions
    		}
    		break;
    	case SEAGRASS:
    		System.out.println("picked up SeaGrass");
    		Boatboard.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates sea grass
    		for(Estuary e: Boatboard.getLapPath()){
    			e.setDamage(0);//resets the damage on all of the Estuaries
    			Boatgame.setDamagePenalty(0);
    		}
    		break;
    	case ROCK:
    		boat.setXLoc(boat.getXLoc() - 30);
    		Boatview.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		Boatview.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		Boatview.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		boat.setSpeed(0);
    		break;
    	default:
    		//no powerUp aka NONE
    		break;
    	}
    }
	
	public static void turnLeftAction(){
		boat.turnLeft();
	}
	
	public static void turnRightAction(){
		boat.turnRight();
	}

	public static void stopTimers() {
		/**
		 * stops all the timers when this game is closed
		 */
		timer.stop();
		powerUpTimer.stop();
		secondTimer.stop();
	}

}
