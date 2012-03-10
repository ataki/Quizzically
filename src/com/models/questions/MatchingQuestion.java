package com.models.questions;

import java.util.List;

public class MatchingQuestion extends BaseQuestion {
	
	public MatchingQuestion(String question, List<String> answers) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MatchingAnswer(answers);
	}
	
	public MatchingQuestion(String question, List<String> answers, int time) {
		this.question = question;
		this.isTimed = true;
		this.time = time;
		this.answer = new MatchingAnswer(answers);
	}
}
