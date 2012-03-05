package com.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import com.backend.DBObject;

public class User extends DBObject {

	public static int INVALID_USER = -1;
	private int id;
	private String email;
	private String name;
	private String description;
	private int numQuizzesTaken;
	private String achievements;
	public TagManager tagManager;
	
	private void setValues(int id, String name, String achievements) {
		this.id = id;
		this.name = name;
		this.achievements = achievements;	
		this.tagManager = new TagManager();
	}

	public User() {
		super(DBObject.userTable);
		setValues(INVALID_USER, "", "");
	}
	
	public User(int id) {
		super(DBObject.userTable);
		id = INVALID_USER;
		StringBuilder query = new StringBuilder();
		query.append("SELECT id, name, achievements FROM " + userTable + " ");
	    query.append("WHERE id = \"" + Integer.toString(id) + "\";");
		
		ResultSet rs = getResults(query.toString());
		try {
			if(rs.next()) setValues(rs.getInt(1), rs.getString(2), rs.getString(3));
			else setValues(INVALID_USER, "", "");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public User(int id, String name, String achievements) {
		super(DBObject.userTable);
		setValues(id, name, achievements);
	}

	public User(String name) {
		super(DBObject.userTable);
		id = INVALID_USER;
		StringBuilder query = new StringBuilder();
		query.append("SELECT id, name, achievements FROM " + userTable + " ");
		query.append("WHERE name = \"" + name + "\";");
		
//		System.out.println(query.toString());
		ResultSet rs = getResults(query.toString());
		try {
			if(rs.next()) setValues(rs.getInt(1), rs.getString(2), rs.getString(3));
			else setValues(INVALID_USER, "", "");
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getId() { return id; }
	public String getUserName() { return name; }
	public String getAchievements() { return achievements; }

	public int getId(String name) {
		return id;
	}

	public static User getUser(String name) {
		return new User(name);
	}
	
	public static User createUser(String name, String password) {
		if(getUser(name).getId() != INVALID_USER) return null;

		Random random = new Random();
		int salt = random.nextInt();
		password = password + Integer.toString(salt);
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			password = hexToString(md.digest(password.getBytes()));

			StringBuilder query = new StringBuilder("INSERT INTO ");
			query.append(userTable + "(");
			query.append("name, password, salt, access, achievements) ");
			query.append( " VALUES(");
			query.append("\"" + name + "\", ");
			query.append("\"" + password + "\", ");
			query.append(salt + ", ");
			query.append("\"0\", ");
			query.append("\"" + "\"); ");

//			System.out.println(query.toString());
			statement.executeUpdate(query.toString());

			return new User(name);
		}
		catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private ArrayList<String> getPassword() {
		StringBuilder query = new StringBuilder();
		query.append("SELECT password, salt FROM " + userTable + " ");
		query.append("WHERE name = \"" + name + "\";");
		
//		System.out.println(query.toString());
		ResultSet rs = getResults(query.toString());
		try {
			ArrayList<String> pwd = new ArrayList<String>();
			if(rs.next()) {
				pwd.add(rs.getString(1));
				pwd.add(rs.getString(2));
				return pwd;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static User authenticateUser(String name, String password) {
		User user = new User(name);
		if(user.getId() == INVALID_USER) return null;

		ArrayList<String> pwd = user.getPassword();
		String passwd = pwd.get(0);
		String salt = pwd.get(1);
		
//		System.out.println("Password = " + passwd + " Salt = " + salt);

		password = password + salt;

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			password = hexToString(md.digest(password.getBytes()));
//			System.out.println("Input password: " + password);
			if(password.equals(passwd)) return user;
		}
		catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String toString() {
		return name;
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
		if (rs.next()) return rs.getString("name");
		else return null;
	}

}
 
