package com.models;

import com.backend.DBObject;

public class Friend extends DBObject {
	private int friendShipId;
	private int friendId1;
	private int friendId2;
	private int type;
	
	Friend(){
		super(DBObject.friendshipTable);
	}
	
	Friend(int friendShopId, int friendId1,int friendId2, int type){
		this.friendShipId = friendShopId;
		this.friendId1=friendId1;
		this.friendId2=friendId2;
		this.type = type;
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
