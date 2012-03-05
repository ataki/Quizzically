package com.models;

import java.sql.SQLException;
import java.sql.Time;

import com.backend.DBObject;
import com.models.Question;

import java.sql.*;
import java.util.List;

/**
 * 
 * @author Sydney
 * Quiz object stores information of a quiz
 */
public class Quiz extends DBObject {
	
	public static String delim = "##";
	
	private int id;
	private String author;
	private Time timestamp;
	private String category;
	private String tags;
	private boolean randomness;
	private int rating;
	private int numRated;
	
	/** A quick way of creating a quiz and syncing it immediately
	 * with the database
	 * @throws SQLException 
	 */
	public int QuizUpload(String author, String category, String tags, boolean randomness) throws SQLException {
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + DBObject.quizTable + " ");
		query.append("VALUE (null," + author + ", NOW()," + category +", "+ randomness+ ",0");
		updateTable(query.toString());
		ResultSet rs = statement.getGeneratedKeys();
		if(rs.next())
			return rs.getInt(1);
		return -1;
		// TODO: PLEASE FILL THIS OUT 
		// THIS IS TOP PRIORITY
		// can use executeBatch for this 
	}
	
	public Quiz() {
		super(DBObject.quizTable);
		// TODO Auto-generated constructor stub
	}
	public Quiz(int id, 
			String author, 
			Time timestamp, 
			String category, 
			String tags, 
			boolean randomness, 
			int rating,
			int numRated) {
		super(DBObject.quizTable);
		this.id = id;
		this.author = author;
		this.timestamp = timestamp;
		this.tags = tags;
		this.randomness = randomness;
		this.rating = rating;
		this.numRated = numRated;
		// TODO Auto-generated constructor stub
	}
	public Quiz getQuiz(int id) throws SQLException{
		StringBuilder query = new StringBuilder();
		query.append("SELECT * FROM " + DBObject.quizTable + " ");
		query.append("WHERE id = "+ id);
		ResultSet rs = getResults(query.toString());
		if(rs.next())
			return null;
		else
			return new Quiz(id,rs.getString("author"),rs.getTime("timestamp"),rs.getString("category"),rs.getString("tags"),rs.getBoolean("randomness"),rs.getInt("rating"),rs.getInt("numRated"));
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * @return the timestamp
	 */
	public Time getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Time timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}
	/**
	 * @return the randomness
	 */
	public boolean isRandomness() {
		return randomness;
	}
	/**
	 * @param randomness the randomness to set
	 */
	public void setRandomness(boolean randomness) {
		this.randomness = randomness;
	}
	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/** Fetches user info from database */
	private String fetchString = "select * from "  + DBObject.quizTable +
								"where ID = ? " + "limit 1";
	public boolean fetch() {
		if(! conPrepare(fetchString)) return false;
		try {
			prepStatement.setInt(1, this.id);
			return this.executePrepared();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return false;							
	}
	
	
	/**
	 * Private Objects
	 */

	/**
	 * Rating Object - used to interact with remote server
	 * to tell a user about the rating  
	 * @author jimzheng
	 */
	public class Rating {
		public int rating;
		public int quizId;
		public Rating(int rating, int quizId) {
			this.rating = rating;
			this.quizId = quizId;
		}
	}
	
	public class Category {
		public String category;
		public int quizId;
		public Category(String category, int quizId) {
			this.category = category;
			this.quizId = quizId;
		}
	}
	
	/**
	 * Database Operations
	 */
	
	private String addRatingString = 
		"update" + DBObject.quizTable + 
		"set RATING = (RATING + ?) / (NUMRATED + 1) " + 
		"	 NUMRATED  = NUMRATED + 1" +
		"where ID = ?";
	
	public boolean addRating(Rating r) {
		if(!this.conPrepare(addRatingString)) return false;
		try {
			prepStatement.setInt(1, r.quizId);
			prepStatement.setInt(2, r.rating);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return this.executePrepared();
	}
	
	private String addCategoriesString = 
		"update" + DBObject.quizTable +
		"set CATEGORY = concat(CATEGORY, ?) " +
		"where ID = ?";
	public boolean addCategories(List<Category> categories) {
		int quizId = categories.get(0).quizId;
		StringBuilder all= new StringBuilder();
		for(Category cat : categories) {
			all.append(cat.category);
			all.append(Quiz.delim);
		}
		
		if(!this.conPrepare(addCategoriesString)) return false;
		try {
			prepStatement.setString(1, all.toString());
			prepStatement.setInt(2, quizId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return this.executePrepared();
	}

}
