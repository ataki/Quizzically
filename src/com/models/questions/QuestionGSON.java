package com.models.questions;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class QuestionGSON {
	
	private static Gson gs = new Gson();
	
	public static String questionToJSON(List<BaseQuestion> questions) {
		return gs.toJson(questions);
	}
	
	public static Object jsonToQuestion(String json) {
		return gs.fromJson(json, ArrayList.class);
	}
	
}
