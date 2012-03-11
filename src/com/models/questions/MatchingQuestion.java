package com.models.questions;

import java.util.ArrayList;
import java.util.List;

public class MatchingQuestion extends BaseQuestion {
	
	List<String> questions = new ArrayList<String>();
	
	public MatchingQuestion(List<String> questions, List<String> answers) {
		this.questions = questions;
		this.isTimed = false;
		this.answer = new MatchingAnswer(answers);
		this.type = QuestionType.Matching;
	}

}
