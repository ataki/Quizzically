package com.backend;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Author: Samir Patel

// this extends thread for future use... in case we want to protect against
// concurrent DB updates... but this is not required.
public class DBObject extends Thread {
	private static final String account = "ccs108wawa3070"; 
	private static final String password = "duhijieb";          
	private static final String server = "mysql-user.stanford.edu";
	private static final String database = "c_cs108_wawa3070"; 

	/*
	+----------------------------+
	| Tables_in_c_cs108_wawa3070 |
	+----------------------------+
	| Quiz_announcement          |
	| Quiz_category              |
	| Quiz_friendship            |
	| Quiz_history               |
	| Quiz_message               |
	| Quiz_question              |
	| Quiz_quiz                  |
	| Quiz_user                  |
	| products                   |
	+----------------------------+
	*/
	
	protected static String announcementTable = "Quiz_announcement";
	protected static String categoryTable = "Quiz_category";
	protected static String friendshipTable = "Quiz_friendship";
	protected static String historyTable = "Quiz_history";
	protected static String messageTable = "Quiz_message";
	protected static String questionTable = "Quiz_question";
	protected static String quizTable = "Quiz_quiz";
	protected static String userTable = "Quiz_user";

	private static Connection connection;
	protected static Statement statement;
	
	public void run() {

	}

	/**
	 * Initialized connection and 
	 */
	private void initConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if(connection == null)
				connection = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
			statement = connection.createStatement();
			statement.executeQuery("USE " + database);
		}
		catch (ClassNotFoundException e) {
				e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected String currentTable;
	
	public DBObject(String table) {
		if(connection == null) initConnection();
		currentTable = table;
	}

	public DBObject() {
		if(connection == null) initConnection();
	}
	
	protected ResultSet getResults(String query) {
		try {
			statement.executeQuery(query);
			ResultSet rs = statement.getResultSet();
			return rs;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}

