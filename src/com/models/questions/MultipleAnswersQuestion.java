package com.models.questions;

import java.util.List;

public class MultipleAnswersQuestion extends BaseQuestion {
	
	public MultipleAnswersQuestion(String question, List<String> answers, boolean random) {
		this.question = question;
		this.isTimed = false;
		this.answer = new MultipleAnswersAnswer(answers, random);
	}
	
}
