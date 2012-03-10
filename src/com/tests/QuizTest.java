package com.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.BeforeClass;
import org.junit.Test;

import com.backend.DBObject;
import com.models.questions.*;

public class QuizTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DBObject db = new DBObject();
	}
	
	@Test
	public void questionTest() {
		
		fail("Not yet implemented");
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testJSON() {
		ArrayList<BaseQuestion> questions1 = new ArrayList<BaseQuestion>();
		questions1.add(new FillInBlanksQuestion("question1", "answer1"));
		questions1.add(new MultipleChoiceQuestion("question2", Arrays.asList("answer1", "answer2")));
		String json = "[{\"isTimed\":false,\"time\":0,\"question\":\"question1\",\"answer\":{\"answer\":\"answer1\"}},{\"isTimed\":false,\"time\":0,\"question\":\"question2\",\"answer\":{\"answers\":[\"answer1\",\"answer2\"]}}]";
		ArrayList<BaseQuestion> questions2 = (ArrayList<BaseQuestion>) QuestionGSON.jsonToQuestion(json);
		//assertEquals(questions1.get(0))
	}
	
}
