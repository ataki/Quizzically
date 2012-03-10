package com.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import com.backend.DBObject;
import com.backend.TagManager;

public class User extends DBObject {
	
	/*
	+-------------+--------------+------+-----+---------+----------------+
	| Field       | Type         | Null | Key | Default | Extra          |
	+-------------+--------------+------+-----+---------+----------------+
	| id          | int(11)      | NO   | PRI | NULL    | auto_increment |
	| username    | varchar(130) | NO   |     | NULL    |                |
	| email       | varchar(130) | NO   |     | NULL    |                |
	| password    | longtext     | NO   |     | NULL    |                |
	| salt        | int(11)      | NO   |     | NULL    |                |
	| admin       | tinyint(1)   | NO   |     | NULL    |                |
	+-------------+--------------+------+-----+---------+----------------+
	 */
	
	public static int INVALID_USER = -1;
	private int id;
	private String email;
	private String username;
	private int numQuizzesTaken;
	private boolean admin;
	public TagManager tagManager;
	 // in minimal mode, only display user's id/username/email/admin
	private boolean minimal = false;

	public static String userDBSelect = "SELECT id, username, email, admin FROM " + userTable + " ";

	private void parseResults(ResultSet rs) {
		try {
			if(rs.next()) setValues(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4));
			else setValues(INVALID_USER, "", "", false);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * sets htis user's fields according to certain fields passed in 
	 */
	private void setValues(int id, String username, String email, boolean admin) {
		this.id = id;
		this.username = username;
		this.setEmail(email);	
		this.admin = admin;
		this.tagManager = new TagManager();
	}

	/**
	 * parses user from a resultset representation; useful
	 * when retrieving lists of users from the database
	 */
	public User(ResultSet rs) {
		super(DBObject.userTable);
		parseResults(rs);
	}
	
	/**
	 * FETCHES a user from the database
	 */
	public User(int id) {
		super(DBObject.userTable);
		id = INVALID_USER;
		StringBuilder query = new StringBuilder();
		query.append(userDBSelect + "WHERE id = \"" + Integer.toString(id) + "\";");
		ResultSet rs = getResults(query.toString());
		parseResults(rs);
	}

	/**
	 * FETCHES a user from the database
	 */
	public User(String username) {
		super(DBObject.userTable);
		id = INVALID_USER;
		StringBuilder query = new StringBuilder();
		query.append(userDBSelect + "WHERE username = \"" + username + "\";");
		ResultSet rs = getResults(query.toString());
		parseResults(rs);
	}
	
	/**
	 * the abbreviated, really short constructor. 
	 * Sets ONLY THESE 4 FIELDS, DON'T GO ACCESSING/
	 * MODIFYING OTHERS
	 */
	public User(int id, String username, String email, boolean admin) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.admin = admin;
		this.minimal = true;
	}

	public String getUserName() { 
		return username; 
	}

	public int getId() {
		return id;
	}

	// we should never return an unauthenticated User object...
	private static User getUser(String name) {
		return new User(name);
	}
	
	/**
	 * Creates and uploads a new User to the database with the given
	 * parameters.
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @return
	 */
	public static User createUser(String name, String email, String password) {
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
			query.append("username, email, password, salt, admin) ");
			query.append( " VALUES(");
			query.append("\"" + name + "\", ");
			query.append("\"" + email + "\", ");
			query.append("\"" + password + "\", ");
			query.append(salt + ", ");
			query.append("0");
			query.append(");");

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
		query.append("WHERE username = \"" + username + "\";");
		
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
		
		password = password + salt;

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			password = hexToString(md.digest(password.getBytes()));
			if(password.equals(passwd)) return user;
		}
		catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}

		return null;
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
	
	public static String getName(int id) throws SQLException {
		String query = "SELECT username FROM " + User.userTable + " WHERE id = " + id;
		statement.executeQuery(query);
		ResultSet rs = statement.getResultSet();
		if (rs.next()) return rs.getString("username");
		else return null;
	}

	public void setAdmin(boolean admin) {
		// need to update database
		this.admin = admin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setNumQuizzesTaken(int numQuizzesTaken) {
		// need to update database
		this.numQuizzesTaken = numQuizzesTaken;
	}

	public int getNumQuizzesTaken() {
		return numQuizzesTaken;
	}

	public void setEmail(String email) {
		// need to update database
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public String getName() {
		return username;
	}
	
	public String toString() {
		return username;
	}

}
