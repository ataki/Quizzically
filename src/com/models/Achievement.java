package com.models;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.backend.DBObject;

/**
 * List of user's achievements.
 * To add one of the preformatted achievements:
 * 	
 * 		Achievement myNewA = Achievement.AMATEUR_AUTHOR(34); // loads new Ach. for user_id 34
 * 		myNewA.upload(); 
 * 		myNewA = null; // free this newly created object
 * 
 * @author jimzheng
 *
 */

public class Achievement extends DBObject {
	private int user_id;
	private String description;
	private String award;
	private String url;
	private Date timestamp;
	private int id;
	
	// urls that point o images of prizes for a certain level of award
	public static String DEFAULT_URL= "http://a.dryicons.com/images/icon_sets/luna_blue_icons/png/128x128/prize_winner.png";
	public static String LVL1_URL = "";
	public static String LVL2_URL = "";
	public static String LVL3_URL = "";
	public static String LVL4_URL = "";
	
	/* the required awards */
	public static Achievement AMATEUR_AUTHOR(int userid) {
		return new Achievement(userid, "You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement PROLIFIC_AUTHOR(int userid) {
		return new Achievement(userid, "You've created 5 quizzes!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement PRODIGIOUS_AUTHOR(int userid) {
		return new Achievement(userid, "You've created 10 quizzes!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement QUIZ_MACHINE(int userid) {
		return new Achievement(userid, "You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement I_AM_THE_GREATEST(int userid) {
		return new Achievement(userid, "You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	public static Achievement PRACTICE_MAKES_PERFECT(int userid) {
		return new Achievement(userid, "You've created a quiz!", "Amateur Author", DEFAULT_URL, new Date());
	}
	
	public Achievement(int user_id, String description, String award, String url, Date date) {
		this.user_id = user_id;
		this.setDescription(description);
		this.setAward(award);
		this.setUrl(url);
		this.setTimestamp(date);
	}

	public Achievement(int user_id, String description, String award, String url, Timestamp timestamp) {
		this.user_id = user_id;
		this.setDescription(description);
		this.setAward(award);
		this.setUrl(url);
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss").parse(timestamp.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setTimestamp(d);
	}
	
	public Achievement(String description, String award, String url, String date) {
		this.setDescription(description);
		this.setAward(award);
		this.setUrl(url);
		Date sdf = null;
		try {
			sdf = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.setTimestamp(sdf);
	}
	
	private String insert = "insert into " + DBObject.achievementTable + 
		" (user_id, quiz_id, score, timeTaken) values (?, ?, ?)";
	/**
	 * inserts into current database
	 * @return
	 */
	public boolean upload() {
		if(! this.conPrepare(insert)) return false;
		try {
			prepStatement.setInt(1, this.user_id);
			prepStatement.setString(2, this.award);
			prepStatement.setString(3, this.description);
			prepStatement.setString(4, this.url);
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
	private String delete = "delete from " + DBObject.achievementTable +
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

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getAward() {
		return award;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setTimestamp(Date date) {
		this.timestamp = date;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}
