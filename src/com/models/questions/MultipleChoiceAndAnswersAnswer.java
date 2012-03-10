package com.models.questions;

import java.util.List;

public class MultipleChoiceAndAnswersAnswer extends BaseAnswer {
	private List<String> choices;
	private List<String> answers;
	public MultipleChoiceAndAnswersAnswer(List<String> answers) {
		this.answers = answers;
	}
	public int checkAnswer(List<String> userAnswers) {		
		return answer.equals(userAnswers.get(0)) == true? 1:0;
	}
}
