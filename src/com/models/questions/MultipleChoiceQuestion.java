package com.models.questions;

import java.util.List;

public class MultipleChoiceQuestion extends BaseQuestion {
	
	public MultipleChoiceQuestion(String question, String answer, List<String> choices) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MultipleChoiceAnswer(answer, choices);
	}
	
}
