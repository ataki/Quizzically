package com.models.questions;

import java.util.ArrayList;
import java.util.List;

public class MatchingAnswer extends BaseAnswer {
	private List<String> answers;
	public MatchingAnswer(List<String> answers){
		this.answers = new ArrayList<String>(answers);
	}
	@Override 
	public int checkAnswer(List<String> userAnswers){
		if(answers.size() != userAnswers.size())
			return 0;
		for(int i = 0; i < userAnswers.size(); i++){
			if(!answers.get(i).equals(userAnswers.get(i)))
				return 0;
		}
		return 1;
	}
}
