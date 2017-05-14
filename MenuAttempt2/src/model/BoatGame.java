package model;

public class BoatGame {
	Integer score = 0;
	Integer time = 120; // one minutes?
	Integer lap = 0;
	
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
	public Integer getScore(){
		/**
		 * returns score value
		 */
		return score;
	}
	public Integer getTime(){
		/**
		 * returns time value
		 */
		return time;
	}
	
	public Integer getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	
	
}
