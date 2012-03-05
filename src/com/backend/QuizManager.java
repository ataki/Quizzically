package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import com.models.Achievement;
import com.models.Quiz;

/**
 * 
 * @author Sydney
 * QuizManager deals with quiz table & question table
 */
public class QuizManager extends DBObject {
	
	public QuizManager(){
		super();
	}
	
	private String base = "select * from " + DBObject.quizTable;

	public List<Quiz> getByUserId(int userid) {
		if(! this.conPrepare(base + filter + limit)) return null;
		try {
			prepStatement.setString(1, "user_id");
			prepStatement.setInt(2, userid);
			ResultSet r = prepStatement.executeQuery();
			
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Quiz> getByCategory(String category) {
		// TODO : FINISH THIS FUNCTION
		// FIRST GET A LIST OF QUIZ ID'S, THEN 
		// GET THE QUIZ FROM THIS
		
		if(! this.conPrepare(base + filter + limit)) return null;
		try {
			prepStatement.setString(1, "quiz_id");
			prepStatement.setInt(2, quizid);
			ResultSet r = prepStatement.executeQuery();
			
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * convert ResultSet >> List
	 */
	private List<Quiz>convertToList(ResultSet r) throws SQLException {
		List<Quiz> result = new ArrayList<Quiz>();
		while(r.next()) {
			result.add(new Quiz(r.getInt("id"), 
								r.getInt("creator_id"),
								r.getString("name"),
								r.getString("description"),
								r.getBoolean("single_page"),
								r.getBoolean("immediate_feedback"),
								r.getBoolean("random"),
								r.getInt("points"),
								r.getDouble("rating"),
								r.getInt("numRated"),
								r.getTimestamp("timestamp"))
								);
		}
		return result;
	}
	
	
	//later when user wants to look at the quizzes belong to certain category, the getQuizes(category) should go here

	
}
