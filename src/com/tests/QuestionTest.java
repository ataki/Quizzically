package com.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.DBObject;
import com.models.Question;

public class QuestionTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DBObject db = new DBObject();
	}
	
	@Test
	public void testQuestion() {
		List<String> texts = Arrays.asList("question1", "question2", "question3");
		List<String> answers = Arrays.asList("answer1", "answer2", "answer3");
		Question question = Question.insert(Question.Type.MultiAnswer.toString(), texts, answers, 1, "http://test.com/");
		assertEquals(Arrays.asList("answer1", "answer2", "answer3"), question.getAnswers());
		
		question.setAnswers(Arrays.asList("answer1", "answer2", "answer4"));
		assertEquals(true, question.sync());
		question = Question.fetch(question.getId());
		assertEquals(Arrays.asList("answer1", "answer2", "answer4"), question.getAnswers());
		
		assertEquals(true, question.delete());
		question = Question.fetch(question.getId());
		assertEquals(null, question);
	}

	@Test
	public void testQuestionManager() {
		fail("Not yet implemented");
	}

}
