package Model;

public class Game {
	int score = 0;
	int time = 120; // two minutes?
	
	public void decreaseScore(){
		score = (score > 0) ? score - 1 : score; // don't decrement 
	}											//score below zero
	public void increaseScore(){
		score++;
	}
	public void decreaseTime(){
		time = (time > 0) ? time - 1 : time; // doesn't decrement
	}										// time below zero
	public int getScore(){
		return score;
	}
	public int getTime(){
		return time;
	}
}
