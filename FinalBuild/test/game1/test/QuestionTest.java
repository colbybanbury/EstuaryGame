package game1.test;

import static org.junit.Assert.*;
import game1.model.Question;

import org.junit.Test;

public class QuestionTest {

	@Test
	public void test() {
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

}
