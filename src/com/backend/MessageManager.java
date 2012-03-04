package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

/**
 * 
 * @author Sydney
 *	handles db Table Quiz_message <-> servlets 
 */
public class MessageManager {

	
	private Statement stmt;
	private final String TABLE = "Quiz_message";
	
	public class Message{
		public int id;
		public int fromUser_id;
		public int toUser_id;
		public String message;
		public boolean read;
		public String messageType;
		public Time timestamp;
	}
	public MessageManager(){
		DBObject db = new DBObject();
		stmt = db.getStatement();
	}
	public void addMessage(int from_id, int to_id, String message, String messageType) throws SQLException{
		StringBuilder str_builder = new StringBuilder();
		str_builder.append("INSERT INTO " +  TABLE + "VALUE (null,");
		str_builder.append(Integer.toString(from_id) + ",");
		str_builder.append(Integer.toString(to_id) + ",");
		str_builder.append(message + ", FALSE,");
		str_builder.append(messageType + ", NOW())");
		stmt.executeQuery(str_builder.toString());
	}
	public void deleteMessage(int id) throws SQLException{
		stmt.executeQuery("DELETE * FROM "+ TABLE + " WHERE id = " + Integer.toString(id));		
	}
	public Message getMessage(int id) throws SQLException{
		ResultSet rs = stmt.executeQuery("SELECT * FROM "+ TABLE + " WHERE id = " + Integer.toString(id));
		rs.next();
		Message msg = new Message();
		msg.id = id;
		msg.fromUser_id = rs.getInt("fromUser_id");
		msg.toUser_id = rs.getInt("toUser_id");
		msg.message = rs.getString("message");
		msg.read = rs.getBoolean("read");
		msg.messageType = rs.getString("messageTyps");
		msg.timestamp = rs.getTime("timestamp");
		return msg;
	}
	public ArrayList<Message> getUserMessages(int id) throws SQLException{
		ResultSet rs = stmt.executeQuery("SELECT * FROM "+ TABLE + " WHERE toUser_id = " + Integer.toString(id));
		ArrayList<Message> msgs = new ArrayList<Message>();
		while(rs.next()){
			Message msg = new Message();
			msg.id = id;
			msg.fromUser_id = rs.getInt("fromUser_id");
			msg.toUser_id = rs.getInt("toUser_id");
			msg.message = rs.getString("message");
			msg.read = rs.getBoolean("read");
			msg.messageType = rs.getString("messageTyps");
			msg.timestamp = rs.getTime("timestamp");
		}
		return msgs;
	}
}
