package com.models.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultipleChoiceAndAnswersAnswer extends BaseAnswer {
	private List<String> choices;
	private List<String> answers;
	public MultipleChoiceAndAnswersAnswer(List<String> answers, List<String> choices) {
		this.answers = new ArrayList<String>(answers);
		this.choices = new ArrayList<String>(choices);
	}
	public int checkAnswer(List<String> userAnswers) {
		Set<String> userAnswersSet = new HashSet<String>(userAnswers);
		Set<String> answersSet = new HashSet<String>(answers);
		userAnswers.retainAll(answersSet);
		return userAnswers.size();
	}
	@Override
	public int points() {
		return answers.size();
	}
	
}
