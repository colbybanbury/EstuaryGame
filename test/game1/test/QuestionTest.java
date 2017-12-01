package game1.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import game1.model.Question;

import org.junit.Test;

public class QuestionTest {

	@Test
	public void test1() {
		String prompt = "Question 1: ";
		String correctAnswer = "Correct";
		String answer1 = "False";
		String answer2 = "Incorrect";
		
		Question q = new Question(prompt, correctAnswer, answer1, answer2);
		
		System.out.println(q.getPrompt());
		System.out.println(q.getAnswers());
		
		assertFalse(q.isRightAnswer(answer1));
		assertTrue(q.isRightAnswer(correctAnswer));
	}
	
	@Test
	public void test2() {
		String prompt = "Question 1: ";
		String correctAnswer = "Correct";
		String answer1 = "False";
		String answer2 = "Incorrect";
		
		Question q = new Question(prompt, correctAnswer, answer1, answer2);
		ArrayList<String> a1 = new ArrayList<>();
		a1.add("no");
		a1.add("probably not");
		
		q.setCorrectAnswer("Different Answer");
		q.setAnswers(a1);
		
		assertFalse(q.isRightAnswer(a1.get(0)));
		assertFalse(q.isRightAnswer(a1.get(0)));
		assertTrue(q.isRightAnswer("Different Answer"));
		assertEquals("Different Answer", q.getCorrectAnswer());
	}

}
