package com.models;

import java.sql.Time;

import com.backend.DBObject;

public class History extends DBObject {
	private int id;
	private int userID_id;
	private int quizID_id;
	private int score;
	private Time timestamp;
	private double timeTaken;
	public History(int id, int userID_id, int quizID_id, int score, Time timestamp,double timeTaken){
		this.id = id;
		this.userID_id = userID_id;
		this.quizID_id = quizID_id;
		this.score = score;
		this.timestamp = timestamp;
		this.timeTaken = timeTaken;
	}
	
}
