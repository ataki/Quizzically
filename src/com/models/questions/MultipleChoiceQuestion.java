package com.models.questions;

import java.util.List;

public class MultipleChoiceQuestion extends BaseQuestion {
	
	public MultipleChoiceQuestion (List<String> answers) {
		this.isTimed = false;
		this.answer = new MultipleChoiceAnswer(answers);
	}
	
	public MultipleChoiceQuestion (int time, List<String> answers) {
		this.isTimed = true;
		this.time = time;
		this.answer = new MultipleChoiceAnswer(answers);
	}
}
