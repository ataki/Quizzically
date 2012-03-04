package com.models;

// import java.sql.Time;
import java.util.Date;
import com.backend.DBObject;

public class Message extends DBObject {
	
	private int id;
	private int fromUserId;
	private int toUserId;
	private String message;
	private boolean read;
	private String messageType;
	private Date timestamp;
	
	public Message(int id, int fromUserId, int toUserId, String message,
			boolean read, String messageType, Date timestamp) {
		super(DBObject.messageTable);
		this.id = id;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.message = message;
		this.read = read;
		this.messageType = messageType;
		this.timestamp = timestamp;
	}
	public int getMessageId() {
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
	public Date getTimestamp() {
		return timestamp;
	}
	
}
