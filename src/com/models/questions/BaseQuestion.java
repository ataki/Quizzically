package com.models.questions;

import java.util.*;

public class BaseQuestion {
	
	protected boolean isTimed;
	protected int time;
	protected String question;
	protected BaseAnswer answer;
	
	protected BaseQuestion() {}
	
	public BaseQuestion(String question, String answer) {
		this.question = question;
		this.isTimed = false;
		this.answer = new BaseAnswer(answer);
	}
	
	public BaseQuestion(String question, String answer, int time) {
		this.question = question;
		this.isTimed = true;
		this.time = time;
		this.answer = new BaseAnswer(answer);
	}
	
	public int checkAnswer(List<String> userAnswers) {
		return answer.checkAnswer(userAnswers);
	}
}
