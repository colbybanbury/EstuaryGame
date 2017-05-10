package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import controller.CrabController;

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
	public Player player;
	public List<Enemy> enemies = new ArrayList<Enemy>();
	public List<Friend> friends = new ArrayList<Friend>();
	int scentTrailDiv = 300;  // number of rectangles that compose a scent trail
	public double wavyFactor = 1;
	public int scentTrailHeight;
	int waveDirection = 1; // 1 = up, -1 = down
	public List<Rectangle> scentTrail = new ArrayList<Rectangle>(scentTrailDiv);
	double progress = 0;
	double[] progressArray = {-1.1, -0.8, -0.5, -0.2, -0.1, 0, 0.1, 0.2, 0.5, 0.8, 1.1};
	public int friendCounter;
	public String[] facts = {"Welcome to Crab Run! Press SPACE to Jump.", 
							"The goal of this game is to fill up the progress bar above.", 
							"You fill up the bar by staying within the scent trail leading you home.", 
							"Avoid fish at all costs or you'll be forced to answer a question.", 
							"Watch out for storms and droughts that make it more difficult to return home. Good Luck!"};
	
	public ArrayList<Question> questions = new ArrayList<Question>();
	
	public int currQuestion = -1;
	private static boolean isDroughtHappening = false;
	private static boolean isStormHappening = false;
	
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
		
		questions.add(new Question("QUESTION 1", "ANSWER 1a", "ANSWER 1b", "ANSWER 1c"));
		questions.add(new Question("QUESTION 2", "ANSWER 2a", "ANSWER 2b", "ANSWER 2c"));
		questions.add(new Question("QUESTION 3", "ANSWER 3a", "ANSWER 3b", "ANSWER 3c"));
		questions.add(new Question("QUESTION 4", "ANSWER 4a", "ANSWER 4b", "ANSWER 4c"));
		questions.add(new Question("QUESTION 5", "ANSWER 5a", "ANSWER 5b", "ANSWER 5c"));
	}
	
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}	
	
	public int getCurrQuestion(){
		return currQuestion;
	}
	public void setCurrQuestion(int cq){
		this.currQuestion = cq;
	}
	
	public double getProgress(){
		return progress;
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
	@SuppressWarnings("static-access")
	public boolean checkCollision(){
		// TODO: write test for no collisions condition
		for (Iterator<Enemy> enemyIterator = enemies.iterator(); enemyIterator.hasNext();){
			Enemy enemy = enemyIterator.next();
			
			if (player.getLocation().intersects(enemy.getLocation())){
				
				CrabController.view.jump.setEnabled(false);
				CrabController.view.jump.setVisible(false);
				
				CrabController.view.answer1.setEnabled(true);
				CrabController.view.answer1.setVisible(true);
				
				CrabController.view.answer2.setEnabled(true);
				CrabController.view.answer2.setVisible(true);
				
				CrabController.view.answer3.setEnabled(true);
				CrabController.view.answer3.setVisible(true);
				
				enemyIterator.remove();
				player.setStarted(false);
				setCurrQuestion((getCurrQuestion() + 1) % questions.size());
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
		scentTrailHeight *= .75;
		isDroughtHappening = true;
	}
	
	public void stopDrought(){
		scentTrailHeight /= .75;
		isDroughtHappening = false;
	}
	
	public boolean maybeAddDrought(){
		return getProgress() > 2*(width-41)/5;
	}
	
	/**
	 * Causes the scent trail to become wavier
	 * 
	 */
	public void storm(){
		wavyFactor += 3;
		scentTrailHeight *= 2/3;
		isStormHappening = true;
	}
	
	public void stopStorm(){
		wavyFactor -= 3;
		scentTrailHeight *= 3/2;
		isStormHappening = false;
	}
	
	public boolean maybeAddStorm(){
		return getProgress() > (width-41)/2;
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
			
		}else if(!player.getFinished()){
			setProgress(0);
		}
	}
	
	public void rectangleUpdate(){
		if (player.getStarted() && !player.getFinished()){
			
			boolean spawnRectangle = true;
			
			//Initializes the yLoc for the next scent trail Rectangle
			double newY = scentTrail.get(scentTrail.size()-1).getY() - waveDirection*wavyFactor;
			
			//Checks to see if scent trail is out of arbitrary boundary, changes direction if so
			if (newY - (2 * scentTrailHeight/3) <= 0){
				waveDirection = -1;
			}else if (newY > height - scentTrailHeight - (2 * scentTrailHeight / 3) - 30){
				waveDirection = 1;
			}
			
			Random rand = new Random();
			
			if(isStormHappening){
				if((rand.nextInt(10) + 1) % 3 == 0){
					waveDirection *= -1;
				}
			}
			
			if(isDroughtHappening){
				if((rand.nextInt(10) + 1) % 7 == 0){
					spawnRectangle = false;
				}
			}
			
			//Creates new Rectangle
			if(spawnRectangle){
				scentTrail.add(new Rectangle(width, (int) newY, width/scentTrailDiv, scentTrailHeight));
			}
				
			//Loops through all Rectangles and increments locations and removes them if they are off screen
			for (Iterator<Rectangle> rectIterator = scentTrail.iterator(); rectIterator.hasNext();){
				Rectangle newRect = rectIterator.next();
				
				newRect.setLocation((int) (newRect.getX() - newRect.getWidth()), (int) newRect.getY());
				
				if (newRect.getX() <= 0){
					rectIterator.remove();
				}
			}
		}
		
		if(getProgress() >= width - 41){
			player.setFinished(true);
			player.setStarted(false);
		}
	}
}
