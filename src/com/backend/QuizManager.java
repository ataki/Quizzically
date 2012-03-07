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
	
	/** base defines the basic select query to use for this Manager.
	 * convertToList must convert based on the same columns
	 * that base contains to convert ResultSet to List
	 */
	private String base = "select * from " + DBObject.quizTable;
	
	private String predicate_sub = " WHERE id IN ";
	
	private String user_id_filter = " WHERE user_id = ?";
	private String name_filter = " WHERE name LIKE ?";
	
	
	
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
	
	/** NOT TESTED
	 * gets list of quizzes based on a specified userid. 
	 */
	public List<Quiz> getByUserId(int userid) {
		if(! this.conPrepare(base + user_id_filter + limit)) return null;
		try {
			// where user_id = userid
			prepStatement.setInt(1, userid);
			ResultSet r = prepStatement.executeQuery();
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** NOT TESTED
	 * Gets a list of quizzes whose names are partial
	 * matches with the queryString
	 */
	public List<Quiz> getSimilarQuizzes(String queryString) {
		if(! this.conPrepare(base + name_filter + limit)) return null;
		try {
			// "where name LIKE queryString"
			prepStatement.setString(1, queryString);
			ResultSet r = prepStatement.executeQuery(); 
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String category_subquery = 
		" ( select quiz_id from " + DBObject.categoryTable + name_filter + " ) ";
	/** NOT TESTED
	 * Does a predicate subquery to find all quizzes that 
	 * have a certain category. Uses the fact that categories
	 * have a quiz_id field.
	 * 
	 * query should be:
	 * select (id, name, description, rating) from Quiz_quiz 
	 * 		where id in (select quiz_id from Quiz_category WHERE name='science') limit 30
	 */
	public List<Quiz> getByCategory(String category) {
		
		if(! this.conPrepare(base + predicate_sub + category_subquery + limit)) return null;
		try {
			// see above query
			prepStatement.setString(1, category);
			System.out.println(prepStatement.toString());
			ResultSet r = prepStatement.executeQuery();
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** NOT TESTED
	 * gets most recently created quizzes 
     * to do so, does order-by-desc query on timestamp*/
	public List<Quiz> getRecent() {
		if(! this.conPrepare(base + recent + limit)) return null;
		try {
			// getRecent doesn't require any parameter substitution
			// "recent" clause orders by TIMESTAMP so make sure that
			// that field name exists for Quiz object; if not this 
			// won't work
			ResultSet r = prepStatement.executeQuery();
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** NOT TESTED
	 * gets most popular by top rating.
	 * 
	 * query: "select * from Quiz_quiz order by RATING desc limit 30"
	 */
	public List<Quiz> getPopular() {
		
		if(! this.conPrepare(base + sorted_desc + limit)) return null;
		try {
			// see above query
			prepStatement.setString(1, "rating");
			ResultSet r = prepStatement.executeQuery();
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
