package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Achievement;
public class AchievementManager extends DBObject {

	/**
	 * Retrieve lists of achievements using following filters:
	 *  - get achievement for ceratin userid
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
	private String filter = " where ? = ?";
					
	public List<Achievement>getAchievement(int userid) {
		if(! this.conPrepare(base + filter)) return null;
		try {
			prepStatement.setString(1, "user_id");
			prepStatement.setInt(2, userid);
			ResultSet r = prepStatement.executeQuery();
			
			List<Achievement> a = new ArrayList<Achievement>();
			while(r.next()) {
				a.add(new Achievement(userid, 
									r.getString("award"),
									r.getString("description"),
									r.getString("url"),
									r.getTimestamp("timestamp"))
									);
			}
			return a;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
