package com.models;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.backend.DBObject;

public class Activity extends DBObject {
	
	/*
	+-----------+--------------+------+-----+---------+----------------+
	| Field     | Type         | Null | Key | Default | Extra          |
	+-----------+--------------+------+-----+---------+----------------+
	| id        | int(11)      | NO   | PRI | NULL    | auto_increment |
	| user_id   | int(11)      | NO   | MUL | NULL    |                |
	| quiz_id   | int(11)      | NO   | MUL | NULL    |                |
	| score     | decimal(3,2) | NO   |     | NULL    |                |
	| timestamp | datetime     | NO   |     | NULL    |                |
	| timeTaken | double       | NO   |     | NULL    |                |
	+-----------+--------------+------+-----+---------+----------------+
	 */
	
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
	
	public Activity() {
		/* default constructor; takes no params */
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
	
	
	private String insert = "insert into " + DBObject.activityTable + 
	" (user_id, quiz_id, score, timeTaken) values (?, ?, ?, ?)";
	/**
	 * inserts into current database
	 * @return
	 */
	public boolean upload() {
		if(! this.conPrepare(insert)) return false;
		try {
			prepStatement.setInt(1, this.user_id);
			prepStatement.setInt(2, this.quiz_id);
			prepStatement.setInt(3, this.score);
			prepStatement.setDouble(3, this.timeTaken);
			prepStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * deletes something only if it exists
	 */
	private String delete = "delete from " + DBObject.activityTable +
						" where user_id = ?";
	public void delete() {
		if(this.id < 1) return; 
		if(! this.conPrepare(insert)) return;
		try {
			prepStatement.setInt(1, this.id);
			prepStatement.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			return;
		}
		return;
	}
}
