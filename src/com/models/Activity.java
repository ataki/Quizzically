package com.models;

import java.sql.Time;

import com.backend.DBObject;

public class Activity extends DBObject {
	private int id;
	private int userID_id;
	private int quizID_id;
	private int score;
	private Time timestamp;
	private double timeTaken;
	public Activity(int id, int userID_id, int quizID_id, int score, Time timestamp,double timeTaken){
		this.setId(id);
		this.setUserID_id(userID_id);
		this.setQuizID_id(quizID_id);
		this.setScore(score);
		this.setTimestamp(timestamp);
		this.setTimeTaken(timeTaken);
	}
	
	public String getQuizName() {
		/* gets the quiz name */
		return null;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUserID_id(int userID_id) {
		this.userID_id = userID_id;
	}

	public int getUserID_id() {
		return userID_id;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void setTimeTaken(double timeTaken) {
		this.timeTaken = timeTaken;
	}

	public double getTimeTaken() {
		return timeTaken;
	}

	public void setTimestamp(Time timestamp) {
		this.timestamp = timestamp;
	}

	public Time getTimestamp() {
		return timestamp;
	}

	public void setQuizID_id(int quizID_id) {
		this.quizID_id = quizID_id;
	}

	public int getQuizID_id() {
		return quizID_id;
	}
	
}
