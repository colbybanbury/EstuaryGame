package model;

public class Game {
	int score = 0;
	int time = 120; // two minutes?
	int lap = 0;
	
	public void decreaseScore(int scoreDown){
		/** 
		 * decreases score by one, cannot go below zero
		 */
		score = (score - scoreDown >= 0) ? score - scoreDown : score; // don't decrement 
	}											//score below zero
	public void increaseScore(int scoreUp){
		/**
		 * increases score by one, no upper bound
		 */
		score+=scoreUp;
	}
	public void decreaseTime(){
		/**
		 * decreases time by one, cannot go below zero
		 */
		time = (time > 0) ? time - 1 : time; // doesn't decrement
	}										// time below zero
	public int getScore(){
		/**
		 * returns score value
		 */
		return score;
	}
	public int getTime(){
		/**
		 * returns time value
		 */
		return time;
	}
	
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	
	
}
