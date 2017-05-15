package game1.model;

import java.util.ArrayList;
import java.util.Collections;

public class Question {
	private String prompt;
	private String correctAnswer;
	private ArrayList<String> answers;
	
	public Question(String p, String cA, String wA1, String wA2){
		this.prompt = p;
		this.correctAnswer = cA;
		
		this.answers = new ArrayList<String>();
		
		this.answers.add(wA1);
		this.answers.add(wA2);
		this.answers.add(cA);
		
		shuffle();
	}
	
	public void shuffle(){
		Collections.shuffle(this.answers);
	}
	
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
