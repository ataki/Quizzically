package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map.Entry;

import com.backend.DBObject;
import com.backend.FriendRecommendation.FriendPair;

public class Friend extends DBObject {
	private int friendShipId;
	private int friendId1;
	private int friendId2;
	private int type;
	private String condition = "SELECT * FROM " + DBObject.friendshipTable + " WHERE user1_id=? AND user2_id=?";
	private String updateFriendship = "UPDATE "+ DBObject.friendshipTable + " SET friendType=? WHERE " +
										"user1_id=? AND user2_id=?"; 
	private String insertFriendship = "INSERT INTO " + DBObject.friendshipTable + " VALUES(null,?,?,?)";
	public Friend(){
		super(DBObject.friendshipTable);
	}
	
	Friend(int friendShopId, int friendId1,int friendId2, int type){
		this.friendShipId = friendShopId;
		this.friendId1=friendId1;
		this.friendId2=friendId2;
		this.type = type;
	}
	
	public boolean insert(int friendId1, int friendId2,int type){
		if(friendId1>friendId2){
			int temp = friendId1;
			friendId1 = friendId2;
			friendId2 = friendId1;
		}
		try {
			String query;
			int[] var = new int[3];			
			
			if(! this.conPrepare(updateFriendship)) return false;
			prepStatement.setInt(1,type);
			prepStatement.setInt(2,friendId1);
			prepStatement.setInt(3,friendId2);			
			System.out.println(prepStatement.toString());
			if(prepStatement.executeUpdate()==0){
			
				if(! this.conPrepare(insertFriendship)) return false;
				prepStatement.setInt(1,friendId1);
				prepStatement.setInt(2,friendId2);
				prepStatement.setInt(3,type);			
				System.out.println(prepStatement.toString());
				prepStatement.execute();
			}
				
			

			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * @return the friendShipId
	 */
	public int getFriendShipId() {
		return friendShipId;
	}

	/**
	 * @param friendShipId the friendShipId to set
	 */
	public void setFriendShipId(int friendShipId) {
		this.friendShipId = friendShipId;
	}

	/**
	 * @return the friendId1
	 */
	public int getFriendId1() {
		return friendId1;
	}

	/**
	 * @param friendId1 the friendId1 to set
	 */
	public void setFriendId1(int friendId1) {
		this.friendId1 = friendId1;
	}

	/**
	 * @return the friendId2
	 */
	public int getFriendId2() {
		return friendId2;
	}

	/**
	 * @param friendId2 the friendId2 to set
	 */
	public void setFriendId2(int friendId2) {
		this.friendId2 = friendId2;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
