package com.models.questions;

import java.util.List;

public class MultipleAnswersQuestion extends BaseQuestion {
	
	public MultipleAnswersQuestion(String question, List<String> answers, boolean random) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MultipleAnswersAnswer(answers, random);
	}
	
	public MultipleAnswersQuestion(String question, List<String> answers, boolean random, int time) {
		this.question = question;
		this.isTimed = true;
		this.time = time;
		this.answer = new MultipleAnswersAnswer(answers, random);
	}
}
