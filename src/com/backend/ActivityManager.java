package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.backend.DBObject;
import com.models.Activity;
public class ActivityManager extends DBObject {
	/*
	 * Methods for retrieving list of activity:
	 *  - by userid
	 *  - by most recent overall
	 *  - on a given day
	 *  - sorted by top performing
	 * 
	 */
	public ActivityManager() {
		/* empty initializer */
	}
	
	private String base = "select * from " + DBObject.activityTable +
					" where ? = ? ";	
	
	public List<Activity>getbyUserId(int userid) {
		if(!this.conPrepare(base)) return null;
		List<Activity> result = null;
		try {
			prepStatement.setString(1, "user");
			prepStatement.setInt(2, userid);
			ResultSet r = this.getPrepared();
			
			result = new ArrayList<Activity>();
			while(r.next()) {
				result.add(new Activity(r.getInt("id"),
										r.getInt("user_id"), 
										r.getInt("quiz_id"),
										r.getInt("score"),
										r.getTimestamp("timestamp"),
										r.getDouble("timeTaken")
									   ));
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
