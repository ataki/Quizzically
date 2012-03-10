package com.models.questions;

import java.util.*;

public class BaseAnswer {
	
	protected String answer;
	public boolean checkAnswer(List<String> userAnswers) {
		return answer.equals(userAnswers.get(0));
	}

}
