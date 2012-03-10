package com.models.questions;

import java.util.List;

public class MultipleChoiceAndAnswersQuestion extends BaseQuestion {
	
	public MultipleChoiceAndAnswersQuestion(String question, List<String> answers, List<String> choices) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MultipleChoiceAndAnswersAnswer(answers, choices);
	}
	
}
