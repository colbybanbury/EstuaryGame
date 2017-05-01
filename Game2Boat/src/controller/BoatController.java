package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
	private final int DELAY = 50;//update rate (50ms) may need to make faster
	
	
	public static Board board;
	public static Boat boat;
	public Game game;
	public static Estuary curEstuary;
	public static POWER_UP curPowerUp;
	public View view;
	private final int LAPLENGTH = 5000;
	private final int RADIUS = (HEIGHT>WIDTH)? 3* WIDTH / 8 : 3*HEIGHT / 8;
	
	
	public BoatController(){
		this.board = new Board(WIDTH, HEIGHT, LAPLENGTH, RADIUS);//adjust values for size of board and length of path
		this.boat = new Boat(board);//adjust values on acceleration, speedInc, and max speed
		this.game = new Game();
		this.curEstuary = board.getLapPath()[0];//starts at the first estuary
		this.view = new View(WIDTH, HEIGHT);
		
		timer = new Timer(DELAY, this);
		timer.start();
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
			//TODO figure out how we want scoring to work
			game.increaseScore();
		}
		game.decreaseTime();
		//TODO if time is up end game
		System.out.println("currently on estuary # " +(boat.getXLoc()*board.getlapDivisions())/board.getLapLength());
		curEstuary = board.getLapPath()[(boat.getXLoc()*board.getlapDivisions())/board.getLapLength()];
		//^finds current estuary. curEsutuary = (xLoc * estuaryCount)/lapLength)
		boat.generateWake(curEstuary); //can return a boolean if it damages it if necessary
		view.animate();
		
		checkCollision();
		//TODO whatever the view needs
		
		
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		onTick();
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
    	int rowNum = (boat.getXLoc()*board.getlapDivisions())/board.getLapLength();
    	int columnNum = (int) ((boat.getRadiusScale() - 0.8)*7.5);
    	//gets what ever powerup is at the boats current location and if there is one there it does whatever it needs to
    	curPowerUp = board.getPowerUps()[rowNum][columnNum];
    	System.out.println("boat is at powerUP["+(boat.getXLoc()*board.getlapDivisions())/board.getLapLength()+"][" + (int) ((boat.getRadiusScale() - 0.8)*7.5) +"]" );
    	//TODO test this to make sure it's on the right powerUp, Did some testing and I think it works
    	switch(curPowerUp){	//TODO the actual implementation of this
    	case OYSTER:
    		System.out.println("picked up an Oyster");
    		board.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates oyster power up
    		//TODO ADD OYSTER FUNCTION
    		break;
    	case SEAGRASS:
    		System.out.println("picked up SeaGrass");
    		board.getPowerUps()[rowNum][columnNum] = POWER_UP.NONE;
    		//activates sea grass
    		//TODO ADD SEAGRASS FUNCTION
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
