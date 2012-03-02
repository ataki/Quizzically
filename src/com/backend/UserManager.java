package com.backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;




/**
 * 
 * @author Sydney
 *	handles db Quiz userTable <-> servlets 
 */
public class UserManager {
	private Statement stmt;
	private final String TABLE = "Quiz_user";
	public class User{
		public int id;
		public String name;
		public String password;
		public int salt;
		public boolean access;
		public String acchievements;
	}
	public UserManager(){
		DBObject db = new DBObject();
		stmt = db.getStatement();
	}
	
	public int getId(String name){
		return 0;
	}
	public ArrayList<User> usersLookup(String name){
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
		return null;
	}
}
