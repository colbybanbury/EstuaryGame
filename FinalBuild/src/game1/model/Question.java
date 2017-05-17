package game1.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class creates the questions and answers that appear when the player collides with an enemy
 */
public class Question {
	private String prompt;
	private String correctAnswer;
	private ArrayList<String> answers;
	
	/**
	 * Creates instance of Question
	 * @param p prompt to display at top of frame
	 * @param cA the correct answer (if the corresponding button is selected, the game will continue)
	 * @param wA1 an incorrect answer (selecting the corresponding button will not progress the game)
	 * @param wA2 an incorrect answer (selecting the corresponding button will not progress the game)
	 */
	public Question(String p, String cA, String wA1, String wA2){
		this.prompt = p;
		this.correctAnswer = cA;
		
		this.answers = new ArrayList<String>();
		
		this.answers.add(wA1);
		this.answers.add(wA2);
		this.answers.add(cA);
		
		shuffle();
	}
	
	/**
	 * shuffles the answer so they will appear in a random order when the prompt appears
	 */
	public void shuffle(){
		Collections.shuffle(this.answers);
	}
	
	/**
	 * determines if answer selected is correct
	 * @param ans answer to test
	 * @return boolean
	 */
	public boolean isRightAnswer(String ans){
		return(ans.equals(correctAnswer));
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}
	
}
