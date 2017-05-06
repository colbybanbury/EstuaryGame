package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author Zachary
 * 
 * This class is the board class that includes all elements that exist in the board and
 * 		are displayed on screen.
 * It updates their positions and checks for any and all collisions.
 */
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
	int friendCounter;
	public String[] facts = {"FACT 1", "FACT 2", "FACT 3", "FACT 4", "FACT 5"};
	public String[] questions = {"QUESTION 1", "QUESTION 2", "QUESTION 3", "QUESTION 4", "QUESTION 5"};
	public String[] answers = {"ANSWER 1", "ANSWER 2", "ANSWER 3", "ANSWER 4", "ANSWER 5"};
	public int currQuestion = -1;
	
	/**
	 * Board constructor.
	 * Initializes the player for the board as well as the List scentTrail that will
	 * contain all the scent trail rectangle.
	 * 
	 * @param width of the board
	 * @param height of the board
	 */
	public Board(int width, int height){
		this.width = width;
		this.height = height;
		this.player = new Player(this);
		this.scentTrailHeight = height / 3;
		scentTrail.add(new Rectangle(width, scentTrailHeight, width/scentTrailDiv, scentTrailHeight));
		
		this.friendCounter = 0;
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
	public int getCurrQuestion(){
		return currQuestion;
	}
	public void setCurrQuestion(int cq){
		this.currQuestion = cq;
	}

	public void setProgress(int progress){
		//TODO: write test for this function
		/* cases:
		 * 		1. progress is less than zero
		 * 		2. progress is greater than width - 41
		 * 		3. progress is between above ranges
		 */
		if (this.progress + progressArray[progress] <= 0){
			this.progress = 0;
		}else if(this.progress + progressArray[progress] >= width - 41){
			this.progress = width - 40;
		}else{
			this.progress += progressArray[progress];
		}
	}
	
	/**
	 * Checks the player's salinity, i.e., collisions with the scent trail.
	 * Is calculated every tick.
	 * Only checks the rectangles within the players x-width.
	 * 
	 * @return salinity- integer total amount of intersected area between the player
	 * 					 and any possible rectangle it could intersect
	 */
	public int checkSalinity(){
		// TODO: write tests for this method
		/* cases:
		 * 		1. player is inside scent trail
		 * 		2. player is outside scent trail
		 * 		3. player is partially in scent trail
		 */
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
	
	/**
	 * Checks to see if the player has collided with an enemy.
	 * Is calculated every tick.
	 * Only checks enemies in the List Enemy
	 * 
	 * @return collided- boolean whether or not player has collided with enemy
	 */
	public boolean checkCollision(){
		// TODO: write test for no collisions condition
		for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
			Enemy enemy = enemyIterator.next();
			
			if (player.getLocation().intersects(enemy.getLocation())){
				enemyIterator.remove();
				player.setStarted(false);
				setCurrQuestion((getCurrQuestion() + 1) % questions.length);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Causes the scent trail to become thinner
	 * 
	 */
	public void drought(){
		scentTrailHeight /= 2;
	}
	
	public void stopDrought(){
		scentTrailHeight *= 2;
	}
	
	/**
	 * Causes the scent trail to become wavier
	 * 
	 */
	public void storm(){
		wavyFactor += 10;
	}
	
	public void stopStorm(){
		wavyFactor -= 10;
	}
	
	/**
	 * Causes the scent trail to become patchy
	 * 
	 */
	public void construction(){
		// TODO: come up with a way to implement holes in scent trail
	}	

	/**
	 * Updates the positions of player, enemies, friends, and scent trail rectangles.
	 * Updates progress of player.
	 * 
	 */
	public void moverUpdate(){
		// TODO: write tests for this method
		/*
		 * cases:
		 * 		1. started
		 * 			a. scentTrail goes off screen up
		 * 			b. scentTrail goes off screen down
		 * 			c. leading rectangle of scentTrail goes off screen (x < 0)
		 * 			d. enemy goes off screen (x < 0)
		 * 			e. friend goes off screen (x < 0)
		 * 			f. enemy is on screen
		 * 				i. collision between player and enemy
		 * 				ii. no collision
		 * 		2. not started (progress = 0)
		 */
		if (player.getStarted()){
			
			//Updates player location
			player.update();
						
			//Updates enemy locations and removes enemies that are off screen
			for(Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
				Enemy e = enemyIterator.next();				

				e.update();
				
				if (e.getXLoc() + e.getLocation().getWidth() <= 0){
					enemyIterator.remove();
				}
			}
			
			//Updates friend locations and removes friends that are off screen
			for(Iterator<Friend> friendIterator = friends.iterator(); friendIterator.hasNext();){
				Friend f = friendIterator.next();

				f.update();
				
				if (f.getXLoc() + f.getLocation().getWidth() + f.getTextSize() <= 0){
					friendIterator.remove();
				}
			}
			
			//Calculates how much of the player is in the scent trail
			//Sets progress bar to increase/decrease accordingly
			setProgress(checkSalinity() / 352);	
			
			//Checks the collisions with the enemies
			if (!enemies.isEmpty()){
				player.setStarted(!checkCollision());
			}
		}else{
			setProgress(0);
		}
	}
	
	public void rectangleUpdate(){
		if (player.getStarted()){	
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
		}
	}
}
