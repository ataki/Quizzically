package com.models;

import java.sql.Time;

import com.backend.DBObject;

public class Announcement extends DBObject {

    private int id;
    private String text;
    private Time timestamp;
    public Announcement(int id, String text, Time timestamp){
    	this.id = id;
    	this.text = text;
    	this.timestamp = timestamp;
    }
    Announcement(){
    	super(DBObject.announcementTable);
    }
	/**
	 * @return the id
	 */
	public int getAnnouncementId() {
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
    
}	
