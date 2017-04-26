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
	int progress = 30;
	int totalProgress = 1000;
	int totalOverlap = 5250;
	
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
	public int getProgress(){
		return progress;
	}
	public void setProgress(int progress){
		this.progress += progress;
	}
	public int getTotalProgress(){
		return totalProgress;
	}
	
	public int checkSalinity(){
		// TODO: only check rectangles the player is in, since player.xLoc does not change
		int totalOverlap = 0;
		Rectangle intersect;
		for (Rectangle r : scentTrail){
			if((r.getX() <= player.getLocation().getX() + player.getLocation().getWidth()) && r.getX() >= player.getLocation().getX()){
				intersect = r.intersection(player.getLocation());
				totalOverlap += intersect.getHeight() * intersect.getWidth();
			}
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
	
	public void update(){		
		for (Enemy e : enemies){
			e.update();
		}
		player.update();
		
		Rectangle endRectangle;
		
		double newY = scentTrail.get(scentTrailDiv-1).getY() - waveDirection*wavyFactor;
		
		if (newY - (2 * scentTrailHeight/3) <= 0){
			waveDirection = -1;
		}else if (newY > height - scentTrailHeight - (2 * scentTrailHeight / 3)){
			waveDirection = 1;
		}
		
		scentTrail.remove(0);
		scentTrail.add(new Rectangle(width, (int) newY, width/scentTrailDiv, scentTrailHeight));
		
		for (Rectangle r: scentTrail){
			r.setLocation((int) r.getX() - (width/scentTrailDiv), (int) r.getY());
		}
		
		setProgress((checkSalinity() / 5250));
		System.out.println("BOARD'S PROGRESS " + progress);
		
		
	} 
}
