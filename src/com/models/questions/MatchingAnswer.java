package com.models.questions;

import java.util.ArrayList;
import java.util.List;

public class MatchingAnswer extends BaseAnswer {
	private List<String> answers;
	public MatchingAnswer(List<String> answers){
		this.answers = new ArrayList<String>(answers);
	}
	@Override 
	public boolean checkAnswer(List<String> userAnswers){
		if(answers.size() != userAnswers.size())
			return false;
		for(int i = 0; i < userAnswers.size(); i++){
			if(answers.get(i)!=userAnswers.get(i))
				return false;
		}
		return true;
	}
}
