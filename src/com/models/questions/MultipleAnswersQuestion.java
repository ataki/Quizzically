package com.models.questions;

import java.util.List;

public class MultipleAnswersQuestion extends BaseQuestion {
	
	public MultipleAnswersQuestion(List<String> answers, boolean random) {
		this.isTimed = false;
		this.answer = new MultipleAnswersAnswer(answers, random);
	}
	
	public MultipleAnswersQuestion(int time, List<String> answers, boolean random) {
		this.isTimed = true;
		this.time = time;
		this.answer = new MultipleAnswersAnswer(answers, random);
	}
}
