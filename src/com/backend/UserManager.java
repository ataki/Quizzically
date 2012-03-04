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

	public UserManager(){
//		DBObject db = new DBObject();
//		stmt = db.getStatement();
	}
	
	public ArrayList<User> usersLookup(String name){

		return null;
	}
}
