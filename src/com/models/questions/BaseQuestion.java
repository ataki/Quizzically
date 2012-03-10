package com.models.questions;

import java.util.*;

public class BaseQuestion {
	
	private String question;
	private BaseAnswer answer;
	
	boolean checkAnswer(List<String> userAnswers) {
		return answer.checkAnswer(userAnswers);
	}
}
