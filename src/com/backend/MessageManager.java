package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;

import com.models.Message;

/**
 * 
 * @author Sydney
 *	handles db Table Quiz_message <-> servlets 
 */
public class MessageManager extends DBObject {

	private final String TABLE = "Quiz_message";
	
	public MessageManager(){
		super();
	}
	public void addMessage(int from_id, int to_id, String message, String messageType) throws SQLException{
	StringBuilder str_builder = new StringBuilder();
		str_builder.append("INSERT INTO " +  TABLE + "VALUE (null,");
		str_builder.append(Integer.toString(from_id) + ",");
		str_builder.append(Integer.toString(to_id) + ",");
		str_builder.append(message + ", FALSE,");
		str_builder.append(messageType + ", NOW())");
		statement.executeQuery(str_builder.toString());
	}
	public void deleteMessage(int id) throws SQLException{
		statement.executeQuery("DELETE * FROM "+ TABLE + " WHERE id = " + Integer.toString(id));		
	}
	/**
	 * 
	 * @param id - message id
	 * @return
	 * @throws SQLException
	 */
	public Message getMessage(int id) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT * FROM "+ TABLE + " WHERE id = " + Integer.toString(id));
		rs.next();
		Message msg = new Message(id,rs.getInt("fromUser_id"),rs.getInt("toUser_id"),rs.getString("message"),rs.getBoolean("read"),rs.getString("messageType"),rs.getTime("timestamp"));
	
		return msg;
	}
	/**
	 * 
	 * @param id - user id
	 * @return a list of message corresponding to an user id
	 * @throws SQLException
	 */
	public ArrayList<Message> getUserMessages(int id) throws SQLException{
		ResultSet rs = statement.executeQuery("SELECT * FROM "+ TABLE + " WHERE toUser_id = " + Integer.toString(id));
		ArrayList<Message> msgs = new ArrayList<Message>();
		while(rs.next()) {
			msgs.add(new Message(id,rs.getInt("fromUser_id"),rs.getInt("toUser_id"),rs.getString("message"),rs.getBoolean("read"),rs.getString("messageType"),rs.getTime("timestamp")));		
		}
		return msgs;
	}
}
