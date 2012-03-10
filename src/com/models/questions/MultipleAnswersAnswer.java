package com.models.questions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultipleAnswersAnswer extends BaseAnswer {
	private List<String> answers;
	private boolean order;
	public MultipleAnswersAnswer(List<String> answers,boolean order){
		this.answers = new ArrayList<String>(answers);
		this.order = order;
	}
	/**
	 * if order is true, checks answers one by one.
	 * if order is true, counts the (answer^userAnswer).size 
	 */
	@Override 
	public int checkAnswer(List<String> userAnswers){
		int score = 0;
		if(order == true){
			for(int i = 0; i < answers.size(); i++){
				if(userAnswers.get(i).equals(answers.get(i))){
					score++;
				}				
			}
		}else{
			//translate userAnswer into a set to distinct answers
			Set<String> userAnswersSet = new HashSet<String>(userAnswers);
			Set<String> answersSet = new HashSet<String>(answers);
			userAnswers.retainAll(answersSet);
			score = userAnswers.size();
		}
		return score;	
		
	}
}
