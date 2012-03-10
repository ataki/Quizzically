package com.models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import com.backend.DBObject;
import com.backend.TagManager;

/**
 * @author Samir
 *
 * The User class models the Quiz_user table.
 * This class supports creating/updating/deleting User records.
 */
public class User extends DBObject {

	/**
	 * Constant identifier for User records not stored in DB.
	 */
	public static final int INVALID_USER = -1;


	/**
	 * Private DB variable mappings
	 */
	private int id;
	private String email;
	private String name;
	private String description;
	private int numQuizzesTaken;
	private String achievements;
	private boolean admin;

	public TagManager tagManager;

	
	public static final String userDBSelect = "SELECT id, name, email, achievements, admin FROM " + userTable + " ";

	private void parseResults(ResultSet rs) {
		try {
			if(rs.next()) setValues(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
			else setValues(INVALID_USER, "", "", "", 0);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param id
	 * @param name
	 * @param email
	 * @param achievements
	 * @param admin
	 */
	private void setValues(int id, String name, String email, String achievements, int admin) {
		this.id = id;
		this.name = name;
		this.setEmail(email);
		this.achievements = achievements;	
		if(admin == 1) { this.setAdmin(true); } else { this.setAdmin(false); }
		this.tagManager = new TagManager();
	}

	/**
	 * 
	 */
	public User() {
		super(DBObject.userTable);
		setValues(INVALID_USER, "", "", "", 0);
	}

	public User(ResultSet rs) {
		super(DBObject.userTable);
		parseResults(rs);
	}
	
	public User(int id) {
		super(DBObject.userTable);
		id = INVALID_USER;
		StringBuilder query = new StringBuilder();
		query.append(userDBSelect + "WHERE id = \"" + Integer.toString(id) + "\";");
		ResultSet rs = getResults(query.toString());
		parseResults(rs);
	}

	public User(String name) {
		super(DBObject.userTable);
		id = INVALID_USER;
		StringBuilder query = new StringBuilder();
		query.append(userDBSelect + "WHERE name = \"" + name + "\";");
		ResultSet rs = getResults(query.toString());
		parseResults(rs);
	}

	public int getId() { return id; }
	public String getUserName() { return name; }
	public String getAchievements() { return achievements; }

	public int getId(String name) {
		return id;
	}

	// we should never return an unauthenticated User object...
	private static User getUser(String name) {
		return new User(name);
	}
	
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
			query.append("name, email, password, salt, access, achievements, admin) ");
			query.append( " VALUES(");
			query.append("\"" + name + "\", ");
			query.append("\"" + email + "\", ");
			query.append("\"" + password + "\", ");
			query.append(salt + ", ");
			query.append("\"0\", ");
			query.append("\"" + "\", ");
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
		query.append("WHERE name = \"" + name + "\";");
		
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
	
	public static String getName(int id) throws SQLException {
		String query = "SELECT name FROM " + User.userTable + " WHERE id = " + id;
		statement.executeQuery(query);
		ResultSet rs = statement.getResultSet();
		if (rs.next()) return rs.getString("name");
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

	public void setDescription(String description) {
		// need to update database
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
