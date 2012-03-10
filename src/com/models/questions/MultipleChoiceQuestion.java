package com.models.questions;

import java.util.List;

public class MultipleChoiceQuestion extends BaseQuestion {
	
	public MultipleChoiceQuestion(String question, List<String> answers) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MultipleChoiceAnswer(answers);
	}
	
	public MultipleChoiceQuestion(String question, List<String> answers, int time) {
		this.question = question;
		this.isTimed = true;
		this.time = time;
		this.answer = new MultipleChoiceAnswer(answers);
	}
}
