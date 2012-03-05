package com.backend;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
	protected static String tagTable = "Quiz_tag";
	protected static String activityTable = "Quiz_activity";
	protected static String achievementTable = "Quiz_achievement";
	
	private static Connection connection;

	// to be used by inherited classes to run queries
	protected static Statement statement;
	protected static PreparedStatement prepStatement;
	
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
	
	protected void updateTable(String query) {
		try {
			statement.executeUpdate(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/** @author Jim Zheng
	 * Updated to include batch queries
	 * and prepared statements
	 * 
	 * Don't use conPrepare if you have 
	 * a query that requires questio marks
	 * as this will detect it as incomplete. 
	 * Instead, just prepare it raw.
	 */
	
	/* common preparedStatement segments */
	protected String filter = " where ? = ?";
	protected String limit = " limit 30 ";
	protected String recent = " order by TIMESTAMP asc ";
	protected String today = " where datediff(now(),TIMESTAMP)=0 and TIMESTAMP <= now() ";
	protected String sorted_asc = " order by ? asc ";
	protected String sorted_desc = " order by ? desc ";
	
	/*
	 * the main reason we have a wrapper for the 
	 * PreparedStatement queries ; so that we can check 
	 * for whehter a template is incomplete
	 */
	protected boolean checkPreparedQuery(String preparedQuery) {
		return (preparedQuery.indexOf('?') != -1);
	}
	
	/* Call first to initialize */
	protected boolean conPrepare(String preparedQuery) {
		if(! checkPreparedQuery(preparedQuery))
			return false;
		try {
			connection.prepareStatement(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/* update */
	protected boolean updateTablePrepared() {
		try {
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/* execute */
	protected boolean executePrepared() {
		try {
			prepStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	protected ResultSet getPrepared() {
		try {
			return prepStatement.executeQuery();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * executeBatch
	 * executing batch queries -- a lot faster than
	 * doing a for-loop with execute stmts
	 * @param batchQuery - a list of queries to execute */
	protected int executeBatch(List<String>batchQuery) {
		int [] results = null;
		try {
			connection.setAutoCommit(false);
			for(String query : batchQuery) {
				statement.addBatch(query);
			}
			results = statement.executeBatch();
			connection.setAutoCommit(true);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		if(results == null) return 0;
		
		int numUpdated = 0;
		for(int i=0;i<results.length;i++)
			numUpdated += results[i];
		return numUpdated;
	}
	protected Connection getConnection(){
		return connection;
	}
	
}

