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
	private String name;
	private String description;
	private Time timestamp;
	private String category;
	private String tags;
	private boolean randomness;
	private int rating;
	private int numRated;
	private String quizUploadString = "INSERT INTO " + DBObject.quizTable +
										" VALUE (null, ?,?,?,NOW(),?,?,0)";
	/** A quick way of creating a quiz and syncing it immediately
	 * with the database
	 * @throws SQLException 
	 */
	public int quizUpload(String author, String name, String description, String category, String tags, boolean randomness) throws SQLException {
		/*StringBuilder query = new StringBuilder();
		query.append("INSERT INTO " + DBObject.quizTable + " ");
		query.append("VALUE (null,\"" + name + "\", \"" + description + "\", \"" + author + "\", NOW(),\"" + category +"\", "+randomness+ ",0)");
		System.out.println(query.toString());*/
		Connection con = getConnection();
		prepStatement = con.prepareStatement(quizUploadString, Statement.RETURN_GENERATED_KEYS);
		try {
			prepStatement.setString(1, name);
			prepStatement.setString(2, description);
			prepStatement.setString(3, author);
			prepStatement.setString(4, category);
			prepStatement.setBoolean(5, randomness);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		System.out.println(prepStatement.toString());
		
		
		prepStatement.executeUpdate();		 	
		ResultSet rs = prepStatement.getGeneratedKeys();
		if(rs.next())
			return rs.getInt(1);
		return -1;
	}
	
	public Quiz() {
		super(DBObject.quizTable);
		// TODO Auto-generated constructor stub
	}
	public Quiz(int id, 
			String author, 
			String name,
			String description,
			Time timestamp, 
			String category, 
			String tags, 
			boolean randomness, 
			int rating,
			int numRated) {
		super(DBObject.quizTable);
		this.id = id;
		this.author = author;
		this.name = name;
		this.description = description;
		this.timestamp = timestamp;
		this.tags = tags;
		this.randomness = randomness;
		this.rating = rating;
		this.numRated = numRated;
		// TODO Auto-generated constructor stub
	}
	public Quiz getQuiz(int id) {
		StringBuilder query = new StringBuilder();
		try {
		query.append("SELECT * FROM " + DBObject.quizTable + " ");
		query.append("WHERE id = "+ id);
		ResultSet rs = getResults(query.toString());
		if(rs.next())
			return new Quiz(id,rs.getString("author"),rs.getString("name"), rs.getString("description"), rs.getTime("timestamp"),rs.getString("category"),null,rs.getBoolean("randomness"),rs.getInt("rating"),0);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
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
	
	/** add rating */
	private String addRatingString = 
		"update " + DBObject.quizTable + 
		" set RATING = (RATING + ?) / (NUMRATED + 1), " + 
		"	 NUMRATED  = NUMRATED + 1" +
		" where ID = ?";
	
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
	
	/** add categories */
	private String addCategoriesString = 
		"update" + DBObject.quizTable +
		" set CATEGORY = concat(CATEGORY, ?) " +
		" where ID = ?";
	
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
