package com.models.questions;

import java.util.*;

public class BaseAnswer {
	
	protected String answer;
	
	public int checkAnswer(List<String> userAnswers) {		
		return answer.equals(userAnswers.get(0)) == true? 1:0;
	}

}
