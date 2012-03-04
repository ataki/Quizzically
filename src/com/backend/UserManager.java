package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * 
 * @author Sydney
 *	handles db Quiz userTable <-> servlets 
 */
public class UserManager extends DBObject{

	public UserManager(){
		super();
	}
	public ArrayList<User> getUsers(String filter) throws SQLException {
		String query = "SELECT * FROM " + currentTable + " WHERE name LIKE \"%" + filter +"%\"";
		ResultSet rs = getResults(query);
			
		ArrayList<User> usersList = new ArrayList<User>();


		while(rs.next()){
			User user = new User(rs.getInt("id"),rs.getString("name"),rs.getString("achievements"));
			usersList.add(user);
		}
		return usersList;


	}

}
