package game1.model;

import java.awt.Rectangle;
import java.util.Random;

/**
 * Fish (or other animals) that obstruct the player and trigger question events
 *
 */
public class Enemy extends Mover{
	private int type;
	static int TYPES = 3; // number of different enemy sprites
	Random rand = new Random();

	/**
	 * Create instance of an Enemy
	 * @param b board to spawn the enemy within
	 */
	public Enemy(Board b){ 
		this.yVel = 0;		
		Random rand = new Random();		
		this.xVel = -23 + rand.nextInt(10);
		this.yAcc = 0;
		int enemyHeight = rand.nextInt(b.height / 2) + b.height/4;
		this.location = new Rectangle(b.width, enemyHeight, 125, 66); // Rectangle(int x, int y, int width, int height)
		this.started = true;
		this.board = b;
		type = rand.nextInt(TYPES);
	}
	
	public int getPicNum(){
		return type;
	}
}
