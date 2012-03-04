package com.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class User extends DBObject {
	
	private int id;
	private String name;
	private String password;
	private int salt;
	private boolean access;
	private String achievements;

	public User() {
		super(DBObject.userTable);
		id = -1;
		name = "";
		achievements = "";
	}
	
	public User(int id, String name, String achievements) {
		super(DBObject.userTable);
		this.id = id;
		this.name = name;
		this.achievements = achievements;
	}
	
	public int getUserId() { return id; }
	public String getUserName() { return name; }
	public String getAchievements() { return achievements; }
		
	public int getId(String name) {
		return 0;
	}
	
	public static boolean createUser(String user, String password) {
		Random random = new Random();
		int salt = random.nextInt();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			System.out.println(hexToString(md.digest(password.getBytes())));
		}
		catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}		
		return false;
	}
	
	// taken from Cracker assignment
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}	
	
	public String getName(int id) throws SQLException {
		String query = "SELECT name FROM " + currentTable + " WHERE id = " + id;
		ResultSet rs = super.getResults(query);
		return rs.getString("name");
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
 
