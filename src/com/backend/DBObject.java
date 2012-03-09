package com.backend;

import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import com.Config;

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
	protected static String friendRecommendationTable = "Quiz_friendRecommendation";
	private static Connection connection;

	// to be used by inherited classes to run queries
	protected static Statement statement;
	protected static PreparedStatement prepStatement;
	
	protected boolean debug;
	
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

		// turn off debuggging if not running
		debug = Config.debug;
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
	 */
	
	/* common preparedStatement segments */
	protected String id_filter = " WHERE id = ?";
	protected String limit = " LIMIT 30 ";
	protected String recent = " ORDER BY TIMESTAMP DESC ";
	protected String today = " WHERE datediff(now(),TIMESTAMP)=0 AND TIMESTAMP <= now() ";
	protected String sorted_asc = " ORDER BY ? ASC ";
	protected String sorted_desc = " ORDER BY ? DESC ";
	
	/*
	 * the main reason we have a wrapper for the 
	 * PreparedStatement queries ; so that we can check 
	 * for whehter a template is incomplete
	 */
	protected boolean checkPreparedQuery(String preparedQuery) {
		return (preparedQuery.indexOf('?') != -1);
	}
	
	/** 
	 * call this wrapper to try and prepare the query.
	 * @return True on success
	 */
	protected boolean conPrepare(String preparedQuery) {
		try {
			prepStatement = connection.prepareStatement(preparedQuery);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/** calls executeUpdate() on a prepStatement;
	 * @return True on success
	 */
	protected boolean updateTablePrepared() {
		try {
			prepStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/** calls execute() on the prepared statement
	 * @return True on success
	 */
	protected boolean executePrepared() {
		try {
			prepStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * executes the prepared statement
	 * and tries to receive a query
	 * @return ResultSet on success, null on failure
	 */
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
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		if(results == null) return 0;
		int numUpdated = 0;
		for(int i=0;i<results.length;i++)
			numUpdated += results[i];
		return numUpdated;
	}
	protected static Connection getConnection(){
		return connection;
	}
	
}

