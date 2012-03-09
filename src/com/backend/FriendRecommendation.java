package com.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
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
	private String base = "";
	private String insertRec = "INSERT INTO " + DBObject.friendRecommendationTable + 
									"VALUE (?,?,?)";
	private String deleteRec = "DELETE * FROM " + DBObject.friendRecommendationTable + 
	"WHERE user1_id = ? OR user2_id = ?";
	private static boolean isRecTableSet = false;
	public class FriendPair extends HashSet<Integer>{
		
		FriendPair(int user1, int user2){
			super.add(user1);
			super.add(user2);
		
		}
		
		
	}
	
	FriendRecommendation(){
		super();
		setFriendRecommendationTable();
	}
	/**
	 * Setup the FriendRecommendation Table based on friendship table.
	 * This should be called only once for initiate the table. 
	 * @return true if the FriendRecommendationTable is set up correctly.
	 */
	public boolean setFriendRecommendationTable(){
		friendsTable = new HashMap<Integer,HashSet<Integer>>();	
		noFriendPairs =  new HashSet<FriendPair>();
		friendsRecTable = new HashMap<FriendPair,Integer>();
		setFriendsTable();
		SetFriendRecTable();
		//*upload to Quiz_friendShip
		try {
			for(Entry<FriendPair, Integer> entry:friendsRecTable.entrySet()){
				if(! this.conPrepare(insertRec)) return false;
				Integer[] pairArray = (Integer[])entry.getKey().toArray();
				prepStatement.setInt(1,pairArray[0]);
				prepStatement.setInt(2,pairArray[1]);
				prepStatement.setInt(3, entry.getValue());
				ResultSet r = prepStatement.executeQuery();
			}	
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return true;	
	}
	/**
	 * This method is called when a friended b, we want to 
	 * remove the corresponding recommendation from the table and update
	 * the statistics.
	 * 
	 * usage:
	 * insertFriendship(a,b,3);
	 * deleteRecommendation(a,b);
	 * @param friendpair
	 */
	public void deleteRecommendation(int user1, int user2){		
		//remove recommendation (a,b) 
		
		//recommend a's friends to b(but not b's friends),
		//recommend b's friends to a(but not a's friends)
	}
	
	public void addRecommendation(FriendPair friendpair){
		//add recommendation (a,b)
		//delete recommendation (a,c) c is a friend of b
		//delete recommemdation (b,c) c is a friend of a
	}
	
	/**
	 * SetFriendRecommendationTable should be only called once
	 * it will fill the friendRecommendationTable based on current Friendship Table.
	 */
	private void setFriendsTable(){
		//get user table from DB
		isRecTableSet = false;
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
