package com.models.questions;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceAnswer extends BaseAnswer {
	private List<String> choices;


	public MultipleChoiceAnswer(String answer,List<String> choices) {
		super(answer);
		this.choices = new ArrayList<String>(choices);
	}
	
	public List<String> getChoices(){
		return choices;
	}
	
}
