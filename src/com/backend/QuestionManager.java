package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.*;

public class QuestionManager extends DBObject {
	
	private String base = "select * from " + DBObject.questionTable;
	
	private boolean debug;
	public QuestionManager() {
		/* empty initializer */
		debug = false;
	}
	
	// NOT TESTED YET
	private List<Question> convertToList(ResultSet rs) throws SQLException {
		List<Question> result = new ArrayList<Question>();
		while (rs.next()) {
			result.add(new Question(rs.getInt("id"), rs.getString("question"), rs.getString("answers"), rs.getString("url"), rs.getString("questionType"), rs.getInt("quiz_id")));
		}
		return result;
	}

	// NOT TESTED YET
	public List<Question> getbyQuizId(int quizId) {
		if(!this.conPrepare(base + filter + limit)) return null;
		try {
			// filter requires a param name and a value
			prepStatement.setString(1, "quiz_id");
			prepStatement.setInt(2, quizId);
			
			if(debug) System.out.println(prepStatement.toString());
			ResultSet r = this.getPrepared();
			
			return convertToList(r);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
