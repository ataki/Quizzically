package com.backend;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Author: Samir Patel

public class DBObject {
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

	// to be used by inherited classes to run queries
	protected static Statement statement;
	
	/**
	 * Initialized connection and statement
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
	
	protected void updateTable(String query) {
		try {
			statement.executeUpdate(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}

