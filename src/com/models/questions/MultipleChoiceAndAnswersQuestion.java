package com.models.questions;

import java.util.List;

public class MultipleChoiceAndAnswersQuestion extends BaseQuestion {
	
	public MultipleChoiceAndAnswersQuestion(String question, List<String> answers) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MultipleChoiceAndAnswersAnswer(answers);
	}
	
	public MultipleChoiceAndAnswersQuestion(String question, List<String> answers, int time) {
		this.question = question;
		this.isTimed = true;
		this.time = time;
		this.answer = new MultipleChoiceAndAnswersAnswer(answers);
	}
}
