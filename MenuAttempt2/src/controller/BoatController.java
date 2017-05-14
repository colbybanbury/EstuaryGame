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
import model.BoatBoard;
import model.Boat;
import model.Estuary;
import model.BoatGame;
import view.BoatView;

public class BoatController implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	final int HEIGHT = (int)screenSize.getHeight() - 50;
	final int WIDTH = (int)screenSize.getWidth();
	
	private Timer timer;
	private Timer powerUpTimer;
	private Timer secondTimer;
	private final int DELAY = 50;//update rate (50ms) may need to make faster
	private final int SECOND_DELAY = 1000;//1000ms  = 1s
	
	public static BoatBoard boatBoard;
	public static Boat boat;
	public static BoatGame boatGame;
	public static Estuary curEstuary;
	public static POWER_UP curPowerUp;
	public BoatView boatView;
	private final int LAPLENGTH = 5000;
	private final int RADIUS = (HEIGHT>WIDTH)? 3* WIDTH / 8 : 3*HEIGHT / 8;
	Random random = new Random();
	private boolean spawn = true;//if a powerUp should spawn or be taken away
	public static boolean end = false; //is the time up
	int wait = 0; //counter for how many powerUps are spawned before they are cleared
	
	
	public BoatController(){
		this.boatBoard = new BoatBoard(WIDTH, HEIGHT, LAPLENGTH, RADIUS);//adjust values for size of board and length of path
		this.boat = new Boat();//adjust values on acceleration, speedInc, and max speed
		this.boatGame = new BoatGame();
		this.curEstuary = boatBoard.getLapPath()[0];//starts at the first estuary
		this.boatView = new BoatView(WIDTH, HEIGHT);
		
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
		if(boat.getXLoc()>= boatBoard.getLapLength()){
			boat.setXLoc(0);
			boatGame.setLap(boatGame.getLap()+1);
			//TODO figure out how we want scoring to work
			boatGame.increaseScore(10);
		}
		System.out.println("currently on estuary # " +(boat.getXLoc()*boatBoard.getLapDivisions())/boatBoard.getLapLength());
		curEstuary = boatBoard.getLapPath()[(boat.getXLoc()*boatBoard.getLapDivisions())/boatBoard.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		boat.generateWake(curEstuary); //can return a boolean if it damages it if necessary
		boatView.animate();
		
		checkCollision();
	}
	
	private void powerUpTick(boolean spawn){
		if(spawn){
			int a = random.nextInt(boatBoard.getLapDivisions());
			int b = random.nextInt(3);
			System.out.println("spawning powerUp");
			if(boatBoard.getPowerUps()[a][b] != POWER_UP.ROCK){//don't replace rocks
				if(random.nextInt(5)==0)
					boatBoard.getPowerUps()[a][b] = POWER_UP.SEAGRASS;//happens less often than oysters
				else
					boatBoard.getPowerUps()[a][b] = POWER_UP.OYSTER;
			}
			
		}
		else{//clear powerups
			System.out.println("De-spawning powerUps");
			for(int i = 0; i<3; i++){
				for(int j = 0; j < boatBoard.getLapDivisions(); j++){
					if(boatBoard.getPowerUps()[j][i] != POWER_UP.ROCK)//but don't clear rocks
						boatBoard.getPowerUps()[j][i] = POWER_UP.NONE;
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
			boatGame.decreaseTime();
			if(boatGame.getTime() <= 0){
				secondTimer.stop();
				end = true;
				//TODO tally up estuary damage at the end of the game and remove points
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
    	int rowNum = (boat.getXLoc()*boatBoard.getLapDivisions())/boatBoard.getLapLength();
    	int columnNum = (int) ((boat.getRadiusScale() - 0.8)*7.5);
    	//gets what ever powerup is at the boats current location and if there is one there it does whatever it needs to
    	curPowerUp = boatBoard.getPowerUps()[rowNum][columnNum];
    	System.out.println("boat is at powerUP["+(boat.getXLoc()*boatBoard.getLapDivisions())/boatBoard.getLapLength()+"][" + (int) ((boat.getRadiusScale() - 0.8)*7.5) +"]" );
    	//TODO test this to make sure it's on the right powerUp, Did some testing and I think it works
    	switch(curPowerUp){
    	case OYSTER:
    		System.out.println("picked up an Oyster");
    		boatBoard.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates oyster power up
    		Estuary tempE;
    		for(int i = -1; i<=1; i++){
    			tempE = boatBoard.getLapPath()[(i+(boat.getXLoc()*boatBoard.getLapDivisions())/boatBoard.getLapLength())%boatBoard.getLapDivisions()];
    			if(tempE.getType()!=3){//as long as the estuary isn't open water
    				tempE.setType(2);}// makes the current estuary and the two adjacent estuaries have gabions
    		}
    		break;
    	case SEAGRASS:
    		System.out.println("picked up SeaGrass");
    		boatBoard.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates sea grass
    		for(Estuary e: boatBoard.getLapPath()){
    			e.setDamage(0);//resets the damage on all of the Estuaries
    		}
    		break;
    	case ROCK:
    		boat.setXLoc(boat.getXLoc() - 30);//TODO make this a bit cleaner. Speed can not be negative so don't do that
    		boatView.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		boatView.animate();
    		boat.setXLoc(boat.getXLoc() - 30);
    		boatView.animate();
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
