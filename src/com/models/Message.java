package com.models;

import java.sql.Time;

import com.backend.DBObject;

public class Message extends DBObject {
	
	public int id;
	public int fromUser_id;
	public int toUser_id;
	public String message;
	public boolean read;
	public String messageType;
	public Time timestamp;
	
}
