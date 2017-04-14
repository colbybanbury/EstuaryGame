package model;

import java.awt.Rectangle;
import java.util.ArrayList;

import java.util.List;

public class Board {
	int width;
	int height;
	Player player = new Player();
	List<Enemy> enemies = new ArrayList<Enemy>();
	int scentTrailDiv = 50;  // number of rectangles that compose a scent trail
	List<Rectangle> scentTrail = new ArrayList<Rectangle>(scentTrailDiv);
	int progress = 0;
	Board(int width, int height){
		this.width = width;
		this.height = height;
		for (int i = 0; i < scentTrailDiv; i++){
			scentTrail.add(new Rectangle(i * width / scentTrailDiv, height / 3, 
										 width / scentTrailDiv, height / 3));
		}
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public int checkSalinity(){
		// TODO: only check rectangles the player is in, since player.xLoc does not change
		int totalOverlap = 0;
		Rectangle intersect;
		for (Rectangle r : scentTrail){
			intersect = r.intersection(player.getLocation());
			totalOverlap += intersect.getHeight() * intersect.getWidth();
		}
		return totalOverlap;
	}
	public boolean checkCollision(){   // changed from UML
		// iterates through enemies on board to check for collisions with
		// player
		for (Enemy enemy: enemies){
			if (player.getLocation().intersects(enemy.getLocation())){
				return true;
			}
		}
		return false;
	}
	void drought(){}
	void storm(){}
	void construction(){}
	void update(){  // moves enemies, player, and scent trail one increment forward
		Rectangle endRectangle;
		for (Enemy e : enemies){
			e.update();
		}
		player.update();
		scentTrail.remove(0);
		
	} 
}
