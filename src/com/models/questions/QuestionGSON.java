package com.models.questions;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.models.Question;

public class QuestionGSON {
	
	private static Gson gs = new Gson();
	
	public static String questionToJSON(ArrayList<BaseQuestion> questions) {
		return gs.toJson(questions);
	}
	
	public static Object jsonToQuestion(String json) {
		return gs.fromJson(json, ArrayList.class);
	}
	
}
