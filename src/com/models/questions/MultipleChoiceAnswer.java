package com.models.questions;

import java.util.List;

public class MultipleChoiceAnswer extends BaseAnswer {

	private List<String> answers;

	public MultipleChoiceAnswer(List<String> answers) {
		this.answers = answers;
	}
	@Override 
	public int checkAnswer(List<String> userAnswers){
		return 1;
	}
	
}
