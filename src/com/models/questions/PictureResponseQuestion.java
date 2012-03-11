package com.models.questions;

public class PictureResponseQuestion extends BaseQuestion {
	
	String imgUrl;
	
	public PictureResponseQuestion(String question, String answer, String imgUrl) {
		super(question, answer);
		this.imgUrl = imgUrl;
		this.answer = new BaseAnswer(answer);
		this.type = QuestionType.PictureResponse;
	}
	
}
