package game2.model;


/**
 * This class is used to keep track of scoring and timing, and transforming the project from
 * a working model into a game with objectives.
 * @author colby
 */
public class Game {
	Integer score = 0;
	Integer time = 60; // one minutes?
	Integer lap = 0;
	Integer damagePenalty = 0;
	
	/** 
	 * decreases score by one, cannot go below zero
	 */
	public void decreaseScore(int scoreDown){
		score = (score - scoreDown >= 0) ? score - scoreDown : score; // don't decrement 
	}										
	//score below zero
	
	/**
	 * increases score by one, no upper bound
	 */
	public void increaseScore(int scoreUp){
		score+=scoreUp;
	}
	/**
	 * decreases time by one, cannot go below zero
	 */
	public void decreaseTime(){
		time = (time > 0) ? time - 1 : time; // doesn't decrement
	}										// time below zero
	
	
	/**
	 * returns score value
	 */
	public Integer getScore(){
		return score;
	}
	/**
	 * returns time value
	 */
	public Integer getTime(){
		return time;
	}
	
	public Integer getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}

	/** 
	 * increases damage penalty by one, done after game finishes to display
	 */
	public void increaseDamagePenalty(int penalty){
		damagePenalty+=penalty;
	}	
	public Integer getDamagePenalty(){
		return damagePenalty;
	}
	public void setDamagePenalty(Integer damagePenalty) {
		this.damagePenalty = damagePenalty;
	}
	
	
}
