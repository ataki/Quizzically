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
	
	public int checkAnswer(List<String> userAnswers) {
		return answer.checkAnswer(userAnswers);
	}
	
	public void setTime(int time) {
		if (time == 0) {
			this.isTimed = false;
		}
		else {
			this.isTimed = true;
			this.time = time;
		}
	}
	
	public int points() {
		return answer.points();
	}
}
