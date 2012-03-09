package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.models.Quiz;
import com.models.User;


/**
 * 
 *	handles db Quiz userTable <-> servlets 
 */
public class UserManager extends DBObject {

	public UserManager(){
		super();
	}
	
	private String base = "select id, name, email, admin from " + DBObject.userTable;
	/**
	 * Takes ResultSet and converts it into List of minimalist
	 * users. These columns must match the base select query columns.
	 * @param r
	 * @return
	 */
	public static List<User> convertToList(ResultSet r) {
		List<User> result = new ArrayList<User>();
		try {
			while(r.next()) {
				result.add(new User(
					r.getInt("id"),
					r.getString("name"),
					r.getString("email"),
					r.getBoolean("admin")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**** Friendship ****/
	private String friend_complex_query = 
			base + 
			"WHERE id IN " + 
			"((SELECT user1_id from Quiz_friendship WHERE friendType = 3 AND user2_id = ?) " +
			"UNION" +
			"(SELECT user2_id from Quiz_friendship WHERE friendType = 3 AND user1_id = ?))";
	/**TODO: NOT TESTED
	 * Gets friends by specified userid
	 * 
	 * query:
	 * SELECT (id, username, email, admin) FROM Quiz_user WHERE id IN 
	 * 	((SELECT user1_id from Quiz_friendship WHERE friendType = 3 AND user2_id = ?) UNION
	 *   (SELECT user2_id from Quiz_friendship WHERE friendType = 3 AND user1_id = ?))
	 * 
	 * The giant subquery finds all id's whose friendships have been confirmed,
	 * and the first select query simply then goes through finding user information
	 * based on that set of user_id's.
	 * 
	 * @param userid
	 * @return
	 */
	public List<User> getFriends(int userid) {
		if(! this.conPrepare(friend_complex_query)) return null;
		try {
			prepStatement.setInt(1, userid);
			prepStatement.setInt(2, userid);
			ResultSet r = prepStatement.executeQuery(); 
			return convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets a subset of users whose names match that filter
	 * Doesn't use the queryset pattern as above
	 */
	public List<User> getSimilarUsers(String filter) throws SQLException {
		String query = User.userDBSelect + " FROM " + currentTable + " WHERE name LIKE \"%" + filter +"%\"";
		ResultSet rs = getResults(query);
			
		ArrayList<User> usersList = new ArrayList<User>();


		while(rs.next()){
			User user = new User(rs);
			usersList.add(user);
		}
		return usersList;


	}

}
