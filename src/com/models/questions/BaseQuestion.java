package com.models.questions;

import java.util.*;

public class BaseQuestion {
	
	protected boolean isTimed;
	protected int time;
	protected String question;
	protected BaseAnswer answer;
	protected QuestionType type;
	
	protected BaseQuestion() {}
	
	public BaseQuestion(String question, String answer) {
		this.question = question;
		this.isTimed = false;
		this.type = QuestionType.QuestionResponse;
		this.answer = new BaseAnswer(answer);
	}
	
	public QuestionType getType() {
		return type;
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
