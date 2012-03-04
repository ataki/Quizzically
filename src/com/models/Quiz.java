package com.models;

import java.sql.Time;

import com.backend.DBObject;
/**
 * 
 * @author Sydney
 * Quiz object stores information of a quiz
 */
public class Quiz extends DBObject {
	private int id;
	private String author;
	private Time timestamp;
	private String category;
	private String tags;
	private boolean randomness;
	private int rating;
	public Quiz() {
		super(DBObject.quizTable);
		// TODO Auto-generated constructor stub
	}
	public Quiz(int id, String author, Time timestamp, String category, String tags, boolean randomness, int rating) {
		super(DBObject.quizTable);
		this.id = id;
		this.author = author;
		this.timestamp = timestamp;
		this.tags = tags;
		this.randomness = randomness;
		this.rating = rating;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the id
	 */
	public int getQuizId() {
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

	
}
