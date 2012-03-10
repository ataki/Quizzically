package com.models.questions;

import java.util.*;

public class BaseAnswer {
	
	protected String answer;
	
	public String getAnswer() {
		return answer;
	}

	public BaseAnswer() {};
	
	public BaseAnswer(String answer) {
		this.answer = answer;
	}
	
	public int checkAnswer(List<String> userAnswers) {		
		return answer.equals(userAnswers.get(0)) == true? 1:0;
	}

}
