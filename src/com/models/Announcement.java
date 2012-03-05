package com.models;

import java.sql.Time;

import com.backend.DBObject;

public class Announcement extends DBObject {

	public enum Importance { 
		LOW(""), MEDIUM("success"), HIGH("error");
		
		String text;
		
		private Importance(String text) {
			this.text = text;
		}
		
		@Override
		public String toString() {
			return text;
		}
	}
	
    private int id;
    private String text;
    private Importance importance;
    private Time timestamp;
    
	public Announcement(int id, String text, int importance, Time timestamp){	
    	this.id = id;
    	this.text = text;
    	switch (importance) {
	    	case 1:
	        	this.importance = Importance.LOW;
	    		break;
	    	case 2:
	    		this.importance = Importance.MEDIUM;
	    		break;
	    	case 3:
	    		this.importance = Importance.HIGH;
	    		break;
    	}
    	this.timestamp = timestamp;
	}
	
    Announcement(){
    	super(DBObject.announcementTable);
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
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
	
    public Importance getImportance() {
		return importance;
	}
    
}	
