package com.models.questions;

import java.util.*;

public class BaseAnswer {
	
	protected String answer;
	
	public BaseAnswer() {};
	
	public BaseAnswer(String answer) {
		this.answer = answer;
	}
	
	public int checkAnswer(List<String> userAnswers) {
		if(userAnswers.size() == 1)
			return answer.toLowerCase().equals(userAnswers.get(0).toLowerCase()) == true? 1:0;
		return 0;
	}

}
