package com.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


import com.models.questions.*;

public class AnswerTest {
	@Test
	public void BaseAnswerTest(){
		BaseAnswer ba = new BaseAnswer("A");
		String[] strAnswer = {"A"};
		List<String> answer = Arrays.asList(strAnswer);
		assertEquals(1,ba.checkAnswer(answer));
	}
	@Test
	public void MatchingAnswerTest() {
		String[] strAnswers = {"A","B","C","D"};
		String[] strWrongAnswers = {"B","C","A","D"};
		List<String> answers = Arrays.asList(strAnswers);
		List<String> wrongAnswers = Arrays.asList(strWrongAnswers);
		MatchingAnswer ma = new MatchingAnswer(answers);
		assertEquals(1,ma.checkAnswer(answers));
		assertEquals(0,ma.checkAnswer(wrongAnswers));		
	}
	
	@Test
	public void  MultipleAnswersAnswerTest(){
		String[] strAnswers = {"A","B","C","D"};
		List<String> answers = Arrays.asList(strAnswers);
		
		String[] reverseStrAnswers = {"D","C","B","A"};
		List<String> reverseAnswers = Arrays.asList(reverseStrAnswers);
		
		String[] mixCaseStrAnswers = {"A","der","d","e"};
		List<String> mixCaseAnswers = Arrays.asList(mixCaseStrAnswers);
		MultipleAnswersAnswer muOrder = new MultipleAnswersAnswer(answers,true);
		MultipleAnswersAnswer mu = new MultipleAnswersAnswer(answers,false);
		//all correct order matters
		assertEquals(4,muOrder.checkAnswer(answers));
		assertEquals(4,mu.checkAnswer(answers));
		assertEquals(0,muOrder.checkAnswer(reverseAnswers));
		assertEquals(4,mu.checkAnswer(reverseAnswers));
		//mixCase
		assertEquals(1,muOrder.checkAnswer(mixCaseAnswers));
		assertEquals(2,mu.checkAnswer(mixCaseAnswers));
	}
	
	
	@Test
	public void  MultipleChoiceAndAnswersAnswerTest(){
		String[] strAnswers = {"A","B","C","D"};
		String[] userStrAnswers = {"A","C","D"};
		List<String> answers = Arrays.asList(strAnswers);
		List<String> choice = Arrays.asList(strAnswers);
		List<String> userAnswers = Arrays.asList(userStrAnswers);
		MultipleChoiceAndAnswersAnswer mca = new MultipleChoiceAndAnswersAnswer(answers,choice);
		assertEquals(3,mca.checkAnswer(userAnswers));
	}
	
	@Test
	public void  MultipleChoiceAnswerTest(){
		String[] strAnswers = {"A","B","C","D"};
		String answer = "B";
		List<String> choice = Arrays.asList(strAnswers);
		List<String> userAnswer = new ArrayList();
		userAnswer.add("B");
		MultipleChoiceAnswer mca = new MultipleChoiceAnswer(answer,choice);
		assertEquals(1,mca.checkAnswer(userAnswer));
		userAnswer.clear();
		userAnswer.add("c");
	
		assertEquals(0,mca.checkAnswer(userAnswer));
	}
	
}
