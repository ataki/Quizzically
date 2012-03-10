package com.models.questions;

import java.util.*;

public class BaseQuestion {
	
	private String question;
	private Answer answer;
	
	boolean checkAnswer(List<String> userAnswers) {
		return answer.checkAnswer(userAnswers);
	}
}
