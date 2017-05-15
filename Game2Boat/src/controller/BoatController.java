package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.Timer;

import enums.POWER_UP;
import model.Board;
import model.Boat;
import model.Estuary;
import model.Game;
import view.View;

public class BoatController implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	final int HEIGHT = (int)screenSize.getHeight() - 50;
	final int WIDTH = (int)screenSize.getWidth();
	
	private Timer timer;
	private Timer powerUpTimer;
	private Timer secondTimer;
	private final int DELAY = 50;//update rate (50ms) may need to make faster
	private final int SECOND_DELAY = 1000;//1000ms  = 1s
	
	public static Board board;
	public static Boat boat;
	public static Game game;
	public static Estuary curEstuary;
	public static POWER_UP curPowerUp;
	public View view;
	private final int LAPLENGTH = 5000;
	private final int RADIUS = (HEIGHT>WIDTH)? 11* WIDTH / 32 : 11*HEIGHT / 32;
	Random random = new Random();
	private boolean spawn;//if a powerUp should spawn or be taken away
	public static boolean end; //is the time up
	int wait; //counter for how many powerUps are spawned before they are cleared
	
	
	public BoatController(){
		spawn = true;
		end = false;
		wait = 0;
		this.board = new Board(WIDTH, HEIGHT, LAPLENGTH, RADIUS);//adjust values for size of board and length of path
		this.boat = new Boat();//adjust values on acceleration, speedInc, and max speed
		this.game = new Game();
		this.curEstuary = board.getLapPath()[0];//starts at the first estuary
		this.view = new View(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		powerUpTimer = new Timer(DELAY*50, this);
		secondTimer = new Timer(SECOND_DELAY, this);
		timer.start();
		powerUpTimer.start();
		secondTimer.start();
		
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
			game.increaseScore(100);
		}
		System.out.println("currently on estuary # " +(boat.getXLoc()*board.getLapDivisions())/board.getLapLength());
		curEstuary = board.getLapPath()[(boat.getXLoc()*board.getLapDivisions())/board.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		boat.generateWake(curEstuary); //can return a boolean if it damages it if necessary
		view.animate();
		
		checkCollision();
	}
	
	private void powerUpTick(boolean spawn){
		if(spawn){
			int a = random.nextInt(board.getLapDivisions());
			int b = random.nextInt(3);
			System.out.println("spawning powerUp");
			if(board.getPowerUps()[a][b] != POWER_UP.ROCK){//don't replace rocks
				if(random.nextInt(5)==0)
					board.getPowerUps()[a][b] = POWER_UP.SEAGRASS;//happens less often than oysters
				else
					board.getPowerUps()[a][b] = POWER_UP.OYSTER;
			}
			
		}
		else{//clear powerups
			System.out.println("De-spawning powerUps");
			for(int i = 0; i<3; i++){
				for(int j = 0; j < board.getLapDivisions(); j++){
					if(board.getPowerUps()[j][i] != POWER_UP.ROCK)//but don't clear rocks
						board.getPowerUps()[j][i] = POWER_UP.NONE;
				}
			}
		}
		
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == timer){
			onTick();
		}
		if(e.getSource() == powerUpTimer){
			spawn = wait < 4;
			powerUpTick(spawn);
			wait = (wait +1) % 5;
		}
		if(e.getSource() == secondTimer){
			game.decreaseTime();
			if(game.getTime() <= 0){
				secondTimer.stop();
				end = true;
				Estuary[] lapPath = board.getLapPath();
				for (int i = 0; i < lapPath.length; i++){
					game.increaseDamagePenalty(lapPath[i].getDamage() * 10);
					
				}
			}
		}
    }
	
    public static void keyPressed(KeyEvent e) {
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
    	int rowNum = (boat.getXLoc()*board.getLapDivisions())/board.getLapLength();
    	int columnNum = (int) ((boat.getRadiusScale() - 0.8)*7.5);
    	//gets what ever powerup is at the boats current location and if there is one there it does whatever it needs to
    	curPowerUp = board.getPowerUps()[rowNum][columnNum];
    	System.out.println("boat is at powerUP["+(boat.getXLoc()*board.getLapDivisions())/board.getLapLength()+"][" + (int) ((boat.getRadiusScale() - 0.8)*7.5) +"]" );
    	switch(curPowerUp){
    	case OYSTER:
    		System.out.println("picked up an Oyster");
    		board.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates oyster power up
    		Estuary tempE;
    		for(int i = -1; i<=1; i++){ 
    			int temp = (i+(boat.getXLoc()*board.getLapDivisions())/board.getLapLength())%board.getLapDivisions();
    			if (temp <0){
    				temp += board.getLapDivisions();
    			}
    			tempE = board.getLapPath()[temp];
    			if(tempE.getType()!=3){//as long as the estuary isn't open water
    				tempE.setType(2);}// makes the current estuary and the two adjacent estuaries have gabions
    		}
    		break;
    	case SEAGRASS:
    		System.out.println("picked up SeaGrass");
    		board.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates sea grass
    		for(Estuary e: board.getLapPath()){
    			e.setDamage(0);//resets the damage on all of the Estuaries
    		}
    		break;
    	case ROCK:
    		boat.setXLoc(boat.getXLoc() - 30);
    		view.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		view.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		view.animate();
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

}
