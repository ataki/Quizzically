package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Tag;

public class TagManager extends DBObject {
	
	public TagManager() {
		/* null initializer*/
	}
	
	private String fetchUserString = 
		"select USERID, TAG from " + DBObject.tagTable +
		"where USERID = ? limit 30";
	
	private String fetchQuizString = 
		"select QUIZID, TAG from " + DBObject.tagTable +
		"where QUIZID = ? limit 30";
	
	public List<Tag>fetchTagsForUser(int userid) {
		if(!this.conPrepare(fetchUserString)) return null;
		ResultSet r;
		List<Tag>result;
		try {
			prepStatement.setInt(1, userid);
			r = prepStatement.executeQuery();
			result = new ArrayList<Tag>();
			while (r.next()) {
					result.add(new Tag(r.getInt("id"),
									r.getInt("quiz_id"),
									r.getInt("user_id"), 
									r.getString("tag")
									));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	public List<Tag>fetchTagsForQuiz(int quizid) {
		if(!this.conPrepare(fetchQuizString)) return null;
		ResultSet r;
		List<Tag>result;
		try {
			prepStatement.setInt(1, quizid);
			r = prepStatement.executeQuery();
			result = new ArrayList<Tag>();
			while (r.next()) {
				result.add(new Tag(r.getInt("id"),
						r.getInt("quiz_id"),
						r.getInt("user_id"), 
						r.getString("tag")
						));
		     }
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
}
