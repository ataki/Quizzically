package com.models.questions;

import java.util.List;

public class MultipleChoiceAndAnswersQuestion extends BaseQuestion {
	
	public MultipleChoiceAndAnswersQuestion(List<String> answers) {
		this.isTimed = false;
		this.answer = new MultipleChoiceAndAnswersAnswer(answers);
	}
	
	public MultipleChoiceAndAnswersQuestion(int time, List<String> answers) {
		this.isTimed = true;
		this.time = time;
		this.answer = new MultipleChoiceAndAnswersAnswer(answers);
	}
}
