package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.models.User;


/**
 * 
 * @author Sydney
 *	handles db Quiz userTable <-> servlets 
 */
public class UserManager extends DBObject {

	public UserManager(){
		super();
	}

	public ArrayList<User> getUsers(String filter) throws SQLException {
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
