package com.models.questions;

import java.util.List;

public class MatchingQuestion extends BaseQuestion {
	
	public MatchingQuestion(List<String> answers) {
		this.isTimed = false;
		this.answer = new MatchingAnswer(answers);
	}
	
	public MatchingQuestion(int time, List<String> answers) {
		this.isTimed = true;
		this.time = time;
		this.answer = new MatchingAnswer(answers);
	}
}
