package com.models;

import java.sql.Time;

import com.backend.DBObject;

public class Message extends DBObject {
	
	private int id;
	private int fromUserId;
	private int toUserId;
	private String message;
	private boolean read;
	private String messageType;
	private Time timestamp;
	
	public int getId() {
		return id;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public int getToUserId() {
		return toUserId;
	}
	public String getMessage() {
		return message;
	}
	public boolean isRead() {
		return read;
	}
	public String getMessageType() {
		return messageType;
	}
	public Time getTimestamp() {
		return timestamp;
	}
	
}
