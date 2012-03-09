package com.tests;

import org.junit.Test;

import com.backend.FriendManager;
import com.models.Friend;

public class FriendTest {
	@Test
	public void insertTest(){
		Friend friend = new Friend();
		friend.insert(1, 2, 2);
		friend.insert(1, 2, 3);//update type
		friend.insert(1, 5, 3);
		friend.insert(1, 6, 3);
		friend.insert(2, 3, 3);
		friend.insert(3, 4, 3);
		friend.insert(3, 7, 3);
		friend.insert(3, 9, 3);
		friend.insert(4, 6, 3);
	}
	
	@Test
	public void getFriendsTest(){
		FriendManager fm = new FriendManager();
		System.out.println(fm.getFriends(3));
		
	}
}
