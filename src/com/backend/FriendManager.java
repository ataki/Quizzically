package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.backend.FriendRecommendation.*;
import com.models.User;

public class FriendManager extends DBObject {
	private int NUMFRIENDS = 30;
	//t1 represents friendshipTable;
	//t2 represents userTable
	private String base = "SELECT t2.id, t2.name, t2.email, t2.admin FROM " + DBObject.friendshipTable + " as t1";
	private String joinedBase = base + " INNER JOIN " + DBObject.userTable + " as t2";
	/**** Friendship ****/
	private String friend_complex_query = 
			joinedBase + 
			" ON (t1.user2_id = t2.id AND" +
			" t1.user1_id = ?)" + 
			"OR (t1.user1_id = t2.id AND" +
			" t1.user2_id = ?)";
	private String all_friends_query = "SELECT * FROM " + DBObject.friendshipTable 
		+ " WHERE friendType = 3";
	public FriendManager(){
		super();
	}
	

	/**TODO: NOT TESTED
	 * Gets friends by specified userid
	 * 
	 * query:
	 * SELECT (t2.id, t2.username, t2.email, t2.admin) FROM Quiz_user WHERE id IN 
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
	public List<User> getFriends(int userId) {
		if(! this.conPrepare(friend_complex_query)) return null;
		try {
			prepStatement.setInt(1, userId);
			prepStatement.setInt(2, userId);
			System.out.println(prepStatement.toString());
			ResultSet r = prepStatement.executeQuery(); 
			return UserManager.convertToList(r);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * Takes ResultSet and converts it into List of minimalist
	 * users. These columns must match the base select query columns.
	 * @param r
	 * @return
	 */

	
	public ArrayList<Integer> getRecommendation(int id){
		return null;
	}
	public ArrayList<Integer> getFriends(String filter){
		return null;//30 friends
	}
	
	public List<FriendRecommendation.FriendPair> getAllFriendship(){
		ResultSet rs = getResults(all_friends_query);
		List<FriendRecommendation.FriendPair> friendships = new ArrayList<FriendRecommendation.FriendPair>();
		FriendRecommendation x = new FriendRecommendation();
		try {
			while(rs.next()){
				FriendPair newPair = x.new FriendPair(rs.getInt("user1_id"),rs.getInt("user2_id"));
			    friendships.add(newPair);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friendships;
	}
	
	
}
