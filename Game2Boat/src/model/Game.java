package model;

public class Game {
	int score = 0;
	int time = 120; // two minutes?
	int lap = 0;
	
	public void decreaseScore(){
		/* 
		 * decreases score by one, cannot go below zero
		 */
		score = (score > 0) ? score - 1 : score; // don't decrement 
	}											//score below zero
	public void increaseScore(){
		/*
		 * increases score by one, no upper bound
		 */
		score++;
	}
	public void decreaseTime(){
		/*
		 * decreases time by one, cannot go below zero
		 */
		time = (time > 0) ? time - 1 : time; // doesn't decrement
	}										// time below zero
	public int getScore(){
		/*
		 * returns score value
		 */
		return score;
	}
	public int getTime(){
		/*
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
