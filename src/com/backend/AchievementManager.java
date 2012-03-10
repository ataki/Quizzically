package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Achievement;
import com.models.Activity;
public class AchievementManager extends DBObject {
	
	/**
	 * Retrieve lists of achievements using following filters:
	 *  - get achievement for certain user_id
	 *  - get achievement for username
	 *  
	 *  You should first initialize this manager on a per-session basis.
	 *  Once initialized, just cal lits methods to get a new List object.
	 *  Very light memory footprint.
	 */
	
	public AchievementManager() {
		/* empty initializer */
	}

	private String base = "select * from " + DBObject.achievementTable;
	
	public List<Achievement> getByUserId(int userid) {
		String filter = " where user_id = ? ";
		if(! this.conPrepare(base + filter + limit)) return null;
		try {
			prepStatement.setInt(1, userid);
			ResultSet r = prepStatement.executeQuery();
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* 
	 * primary retrieval method; converts ResultSet to ordered
	 * list of Achievements
	 */
	private List<Achievement>convertToList(ResultSet r) throws SQLException {
		List<Achievement> result = new ArrayList<Achievement>();
		while(r.next()) {
			result.add(new Achievement(r.getInt("user_id"), 
								r.getString("award"),
								r.getString("description"),
								r.getString("url"),
								r.getTimestamp("timestamp"))
								);
		}
		return result;
	}
	
}
