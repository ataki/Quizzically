package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User extends DBObject {
	
	private int id;
	private String name;
	private String password;
	private int salt;
	private boolean access;
	private String acchievements;

//	DBObject db = new DBObject();
//	stmt = db.getStatement();

	public User() {
		super(DBObject.userTable);
	}
	
	public int getId(String name) {
		return 0;
	}
	
	public boolean createUser(String user, String password) {
		return false;
	}
	
	public String getName(int id) {
		return "";
	}
	


//	DB implemenation in User.java  getUsers(String userFilter);
/*
	try {
		stmt.executeQuery("SELECT * FROM "+ TABLE +" WHERE name LIKE \"%" + name +"%\"" );
		ResultSet rs = stmt.getResultSet();
		
		ArrayList<User> usersList = new ArrayList<User>();
		while(rs.next()){
			User user = new User();
			user.id = rs.getInt("id");
			user.name = rs.getString("name");
			user.password = rs.getString("password");
			user.salt = rs.getInt("salt");
			user.access = rs.getBoolean("access");
			usersList.add(user);
			return usersList;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	*/
	
}
 