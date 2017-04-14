package model;

import java.util.ArrayList;

import java.util.List;

public class Board {
	int width;
	int height;
	Player player;
	List<Enemy> enemies = new ArrayList<Enemy>();
	int[][] scentTrail;
	int progress;
	Board(int width, int height){
		this.width = width;
		this.height = height;
		scentTrail = new int[width][height];
		progress = 0;
		player = new Player();
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void checkSalinity(){}
	// iterates through enemies on board to check for collisions with
	// player
	public boolean checkCollision(){   // changed from UML
		for (Enemy enemy: enemies){
			if (enemy.getXLoc() <= player.getXLoc() + player.getImgWidth()){
				return true;
			}
		}
		return false;
	}
	void drought(){}
	void storm(){}
	void construction(){}
}
