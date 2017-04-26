package model;

import java.awt.Rectangle;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class Board {
	int width;
	int height;
	Random rand = new Random();
	public Player player;
	public List<Enemy> enemies = new ArrayList<Enemy>();
	public List<Friend> friends = new ArrayList<Friend>();
	int scentTrailDiv = 500;  // number of rectangles that compose a scent trail
	public double wavyFactor = 1;
	public int scentTrailHeight;
	int waveDirection = 1; // 1 = up, -1 = down
	public List<Rectangle> scentTrail = new ArrayList<Rectangle>(scentTrailDiv);
	int progress = 0;
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		this.player = new Player(this);
		this.scentTrailHeight = height / 3;
		for (int i = 0; i < scentTrailDiv; i++){
			scentTrail.add(new Rectangle(i * width / scentTrailDiv, scentTrailHeight, width / scentTrailDiv + 1, scentTrailHeight));
		}
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	
	public int checkSalinity(){ // changed from UML
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
	public void drought(){
		scentTrailHeight /= 2;
	}
	public void storm(){
		wavyFactor = 10;
	}
	public void construction(){}
	
	public void update(){  // moves enemies, player, and scent trail one increment forward		
		for (Enemy e : enemies){
			e.update();
		}
		player.update();
		
		Rectangle endRectangle;
		// check where to place next rectangle in scentTrail
		double newY = scentTrail.get(scentTrailDiv-1).getY() - waveDirection*wavyFactor;
		// if we hit the top, start moving down
		if (newY <= 0) waveDirection = -1;
		// if we hit the bottom, start moving back up
		else if (newY > height - scentTrailHeight) waveDirection = 1;		
		scentTrail.remove(0);
		scentTrail.add(new Rectangle(width, (int) newY, width/scentTrailDiv, scentTrailHeight));
		
		for (Rectangle r: scentTrail){
			r.setLocation((int) r.getX() - (width/scentTrailDiv), (int) r.getY());
		}
	} 
}
