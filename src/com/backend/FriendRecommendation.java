package com.backend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 * 
 * @author Sydney
 * This is a simple recommendation system based on the number of shared friends
 * between two users. 
 */
public class FriendRecommendation {
	
	private HashMap<Integer,HashSet<Integer>> friendsTable;
	private HashMap<FriendPair,Integer>	friendsRecTable;
	private HashSet<FriendPair> noFriendPairs;
	private int NUM_RECOMMENDATIONS = 5;
	
	public class FriendPair extends HashSet<Integer>{
		
		FriendPair(int user1, int user2){
			super.add(user1);
			super.add(user2);
		}
		
		
	}
	
	FriendRecommendation(){
		friendsTable = new HashMap<Integer,HashSet<Integer>>();	
		noFriendPairs =  new HashSet<FriendPair>();
		friendsRecTable = new HashMap<FriendPair,Integer>();
	}
	
	public void setFriendsTable(){
		//get user table from DB
		//right now, read the user table from test dataset
		File file = new File("src\\com\\backend\\RecTestSet.txt");
		try {
			HashSet<Integer> friendsSet;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()){
				int from = scanner.nextInt();
				int to = scanner.nextInt();
				noFriendPairs.add(new FriendPair(from,to));
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
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public ArrayList<FriendPair> FindTuples(HashSet<Integer> users){
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
	public void SetFriendRecTable(){
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
