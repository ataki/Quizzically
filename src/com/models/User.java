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
	// private int numQuizzesTaken;  -- not needed... should get count from Activity table
	private boolean admin;
	public TagManager tagManager;
	 // in minimal mode, only display user's id/username/email/admin
	private boolean minimal = false;

	private String setEmail; // new email address
	private boolean setAdmin; // new admin setting

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
	 * sets this user's fields according to certain fields passed in 
	 */
	private void setValues(int id, String username, String email, boolean admin) {
		this.id = id;
		this.username = username;
		this.email = email;	
		this.admin = admin;
		this.tagManager = new TagManager();
		setAdmin = admin;
		setEmail = email;
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
	
	/**
	 * Synchronizes changes in DB for admin and email
	 * Password reset is isolated to password reset method
	 */
	public void sync() {
		StringBuilder query = new StringBuilder();
		query.append(" UPDATE " + userTable + " ");
		query.append(" SET ");
		query.append(" email = \"" + setEmail + "\" ");
		query.append(" , ");
		if(setAdmin) query.append(" admin = 1 ");
		else query.append(" admin = 0 ");
		query.append(" WHERE id = " + id);
		query.append(";");
		updateTable(query.toString());
		admin = setAdmin;
		email = setEmail;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return username;
	}

	public String getUserName() { 
		return username; 
	}

	public String getEmail() {
		return email;
	}
	
	public boolean isAdmin() {
		return admin;
	}

	public void setEmail(String email) {
		setEmail = email;
	}

	public void setAdmin(boolean admin) {
		setAdmin = admin;
	}

	/**
	 * Resets password, takes immediate effect
	 * @param newPassword
	 */
	public void resetPassword(String newPassword) {
		Random random = new Random();

		int salt = random.nextInt();
		String password = newPassword + Integer.toString(salt);
		MessageDigest md;

		try {
			md = MessageDigest.getInstance("SHA");
			password = hexToString(md.digest(password.getBytes()));

			StringBuilder query = new StringBuilder();
			query.append(" UPDATE " + userTable + " ");
			query.append(" SET ");
			query.append(" password = \"" + password + "\" ");
			query.append(" , ");
			query.append(" salt = \"" + salt + "\" ");
			query.append(" WHERE id = " + id);
			query.append(";");
			updateTable(query.toString());
		}
		catch(NoSuchAlgorithmException e) { 
			e.printStackTrace();
		}
	}

	/* Not needed... should count from Activity table
	public void setNumQuizzesTaken(int numQuizzesTaken) {
		// need to update database
		this.numQuizzesTaken = numQuizzesTaken;
	}

	public int getNumQuizzesTaken() {
		return numQuizzesTaken;
	}
*/
	
	private static User getUser(String name) {
		return new User(name);
	}
	
//	public static String getName(int id) throws SQLException {
//		String query = "SELECT username FROM " + User.userTable + " WHERE id = " + id;
//		statement.executeQuery(query);
//		ResultSet rs = statement.getResultSet();
//		if (rs.next()) return rs.getString("username");
//		else return null;
//	}
	
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
	
	public String toString() {
		if(id == INVALID_USER) return "THIS IS AN INVALID USER!!";
		return username;
	}

}
