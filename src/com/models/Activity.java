package com.models;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.backend.DBObject;

public class Activity extends DBObject {
	
	/* if id is invalid, means that this was not 
	 * a retrieved object.
	 */
	public static final int INVALID = -1;
	
	private int id;
	private int user_id;
	private int quiz_id;
	private int score;
	private Date timestamp;
	private double timeTaken;
	private String username = null;
	
	/** some generic pre-defined activities */
	public static Activity TOOK_QUIZ(int user_id, int quiz_id, int score, double timeTaken) {
		return new Activity(Activity.INVALID, user_id, quiz_id, score, null, timeTaken);
	}
	public static Activity CREATED_QUIZ(int user_id, int quiz_id, int score, double timeTaken) {
		return new Activity(Activity.INVALID, user_id, quiz_id, Activity.INVALID, null, Activity.INVALID);
	}
	
	public Activity(int id, int user_id, int quiz_id, int score, Date timestamp,double timeTaken){
		this.setId(id);
		this.setUser_id(user_id);
		this.setQuiz_id(quiz_id);
		this.setScore(score);
		this.setTimestamp(timestamp);
		this.setTimeTaken(timeTaken);
	}
	
	public Activity(int id, int user_id, int quiz_id, int score, Date timestamp,double timeTaken, String username){
		this.setId(id);
		this.setUser_id(user_id);
		this.setQuiz_id(quiz_id);
		this.setScore(score);
		this.setTimestamp(timestamp);
		this.setTimeTaken(timeTaken);
		this.setUsername(username);
	}
	
	public Activity(int id, int user_id, int quiz_id, int score, Timestamp timestamp, double timeTaken){
		this.setId(id);
		this.setUser_id(user_id);
		this.setQuiz_id(quiz_id);
		this.setScore(score);
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").parse(timestamp.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setTimestamp(d);
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

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getUser_id() {
		return user_id;
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

	public void setTimestamp(Date timestamp2) {
		this.timestamp = timestamp2;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setQuiz_id(int quiz_id) {
		this.quiz_id = quiz_id;
	}

	public int getQuiz_id() {
		return quiz_id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	
}
