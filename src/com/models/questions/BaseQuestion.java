package com.models.questions;

import java.util.*;

public class BaseQuestion {
	
	protected boolean isTimed;
	protected int time;
	protected String question;
	protected BaseAnswer answer;
	
	protected BaseQuestion() {}
	
	public BaseQuestion(String answer) {
		this.isTimed = false;
		this.answer = new BaseAnswer(answer);
	}
	
	public BaseQuestion(int time, String answer) {
		this.isTimed = true;
		this.time = time;
		this.answer = new BaseAnswer(answer);
	}
	
	public int checkAnswer(List<String> userAnswers) {
		return answer.checkAnswer(userAnswers);
	}
}
