package model;

import java.awt.Rectangle;
import java.util.Random;

public class Enemy extends Mover{
	private int type;
	static int TYPES = 3; // number of different enemy sprites
	Random rand = new Random();

	public Enemy(Board b){ 
		this.yVel = 0;
		this.xVel = -18; //TODO: We need to decide how fast these guys go
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
