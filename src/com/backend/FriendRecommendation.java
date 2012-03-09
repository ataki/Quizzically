package com.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

/**
 * 
 * @author Sydney
 * This is a simple recommendation system based on the number of shared friends
 * between two users. 
 */
public class FriendRecommendation extends DBObject{
	
	private HashMap<Integer,HashSet<Integer>> friendsTable;
	private HashMap<FriendPair,Integer>	friendsRecTable;
	private HashSet<FriendPair> noFriendPairs;
	private int NUM_RECOMMENDATIONS = 30;
	
	public class FriendPair extends HashSet<Integer>{
		
		FriendPair(int user1, int user2){
			super.add(user1);
			super.add(user2);
		
		}
		
		
	}
	
	FriendRecommendation(){
		super();

	}
	
	public void setFriendshipTable(){
		friendsTable = new HashMap<Integer,HashSet<Integer>>();	
		noFriendPairs =  new HashSet<FriendPair>();
		friendsRecTable = new HashMap<FriendPair,Integer>();
		setFriendsTable();
		SetFriendRecTable();
		//*sync up with Quiz_friendShip
	}
	/**
	 * SetFriendRecommendationTable should be only called once
	 * it will fill the friendRecommendationTable based on current Friendship Table.
	 */
	private void setFriendsTable(){
		//get user table from DB
		FriendManager fm = new FriendManager();
		HashSet<Integer> friendsSet;
		List<FriendPair> friendships = fm.getAllFriendship();
		for(FriendPair pair : friendships){
			Integer[] pairArray = (Integer[]) pair.toArray();
			int from = pairArray[0];
			int to = pairArray[1];
			noFriendPairs.add(pair);
			if(friendsTable.containsKey(from)){
				friendsSet = friendsTable.get(from);
				friendsSet.add(to);	
			}else{
				friendsSet = new HashSet<Integer>();
				friendsSet.add(to);
				friendsTable.put(from, friendsSet);
			}
			if(friendsTable.containsKey(to)){
				friendsSet = friendsTable.get(to);
				friendsSet.add(from);	
			}else{
				friendsSet = new HashSet<Integer>();
				friendsSet.add(from);
				friendsTable.put(to, friendsSet);
			}
				
		}
			
		
	}
	private ArrayList<FriendPair> FindTuples(HashSet<Integer> users){
		int tempi, tempj;
		ArrayList<FriendPair> combinations = new ArrayList<FriendPair>();
		FriendPair newPair;
		ArrayList<Integer> users_list = new ArrayList<Integer>(users);
		for(int i = 0; i < users_list.size();i++){
			tempi = users_list.get(i);
			for(int j = i+1; j < users_list.size(); j++){
				tempj = users_list.get(j);	
				
				newPair = new FriendPair(tempi,tempj);
				if(!noFriendPairs.contains(newPair))
					combinations.add(newPair);	
			}
				
		}
		return combinations;
		
	}
	private void SetFriendRecTable(){
		for(int key: friendsTable.keySet()){
			ArrayList<FriendPair> toFriendPairs = FindTuples(friendsTable.get(key));
			for(FriendPair pair: toFriendPairs){
				if(friendsRecTable.containsKey(pair)){
					int currentVal = friendsRecTable.get(pair);
					friendsRecTable.put(pair, currentVal+1);
				}else
					friendsRecTable.put(pair,1);
			}	
		}			
	}
	public ArrayList<FriendPair> getRecommendation(){
		ArrayList<FriendPair> recommendations = new ArrayList<FriendPair>(friendsRecTable.keySet());
		Collections.sort(recommendations, new Comparator<FriendPair>(){

			@Override
			public int compare(FriendPair arg0, FriendPair arg1) {				
				return friendsRecTable.get(arg1)- friendsRecTable.get(arg0);
			}
		});
		return recommendations;
		
		
	}
	public HashMap<Integer,HashSet<Integer>> getFriendsTable(){
		return friendsTable;
	}
	
	
	
}
