package com.models.questions;

public class PictureResponseQuestion extends BaseQuestion {
	
	String imgUrl;
	
	public PictureResponseQuestion(String answer, String imgUrl) {
		super(answer);
		this.imgUrl = imgUrl;
		this.answer = new BaseAnswer(answer);
	}
	
	public PictureResponseQuestion(int time, String answer, String imgUrl) {
		super(time, answer);
		this.imgUrl = imgUrl;
		this.answer = new BaseAnswer(answer);
	}
}
