package com.models.questions;

import java.util.ArrayList;
import java.util.List;

public class MultipleAnswersAnswer extends BaseAnswer {
	private List<String> answers;
	private boolean order;
	public MultipleAnswersAnswer(List<String> answers,boolean order){
		this.answers = new ArrayList<String>(answers);
		this.order = order;
	}
	@Override 
	public int checkAnswer(List<String> userAnswers){
		return 1;
	}
}
