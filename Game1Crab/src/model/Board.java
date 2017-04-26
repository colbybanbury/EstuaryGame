package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Board {
	int width;
	int height;
	Random rand = new Random();
	public Player player;
	public List<Enemy> enemies = new ArrayList<Enemy>();
	public List<Friend> friends = new ArrayList<Friend>();
	int scentTrailDiv = 300;  // number of rectangles that compose a scent trail
	public double wavyFactor = 1;
	public int scentTrailHeight;
	int waveDirection = 1; // 1 = up, -1 = down
	public List<Rectangle> scentTrail = new ArrayList<Rectangle>(scentTrailDiv);
	double progress = 0;
	double[] progressArray = {-1.6, -1.0, -0.6, -0.4, -0.2, 0, 0.2, 0.4, 0.6, 1.0, 1.6};
	
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		this.player = new Player(this);
		this.scentTrailHeight = height / 3;
		scentTrail.add(new Rectangle(width, scentTrailHeight, width/scentTrailDiv, scentTrailHeight));
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public double getProgress(){
		return progress;
	}
	public void setProgress(int progress){
		
		if (this.progress + progressArray[progress] <= 0){
			this.progress = 0;
		}else if(this.progress + progressArray[progress] >= width - 40){
			this.progress = width - 40;
		}else{
			this.progress += progressArray[progress];
		}
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
		if (totalOverlap > 0){
			return totalOverlap;
		}else{
			return 0;
		}
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
		//Updates enemy locations
		for (Enemy e : enemies){
			e.update();
		}
		//Updates player location
		player.update();
		
		//Initializes the yLoc for the next scent trail Rectangle
		double newY = scentTrail.get(scentTrail.size()-1).getY() - waveDirection*wavyFactor;
		
		//Checks to see if scent trail is out of arbitrary boundary, changes direction if so
		if (newY - (2 * scentTrailHeight/3) <= 0){
			waveDirection = -1;
		}else if (newY > height - scentTrailHeight - (2 * scentTrailHeight / 3)){
			waveDirection = 1;
		}
		
		//Creates new Rectangle
		scentTrail.add(new Rectangle(width, (int) newY, width/scentTrailDiv, scentTrailHeight));
		
		//Loops through all Rectangles and increments locations and removes them if they are off screen
		for (Iterator<Rectangle> rectIterator = scentTrail.iterator(); rectIterator.hasNext();){
			Rectangle newRect = rectIterator.next();
			
			newRect.setLocation((int) (newRect.getX() - newRect.getWidth()), (int) newRect.getY());
			
			if (newRect.getX() <= 0){
				rectIterator.remove();
			}
		}
		
		//Calculates how much of the player is in the scent trail
		//Sets progress bar to increase/decrease accordingly
		setProgress(checkSalinity() / 352);	
	} 
}
