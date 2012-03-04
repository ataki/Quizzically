package com.backend;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


// Author: Samir Patel
	

public class DBObject  {
	private final String account = "ccs108wawa3070"; 
	private final String password = "duhijieb";          
	private final String server = "mysql-user.stanford.edu";
	private final String database = "c_cs108_wawa3070"; 
	private Statement stmt;
	/*
	+----------------------------+
	| Tables_in_c_cs108_wawa3070 |
	+----------------------------+
	| Quiz_announcement          |
	| Quiz_category              |
	| Quiz_friendship            |
	| Quiz_history               |
	| Quiz_message               |
	| Quiz_question              |
	| Quiz_quiz                  |
	| Quiz_user                  |
	| products                   |
	+----------------------------+
	*/

	public DBObject() {
    	try {
			Class.forName("com.mysql.jdbc.Driver");
	    	Connection con = DriverManager.getConnection( "jdbc:mysql://" + server, account ,password);
	    	stmt = con.createStatement();
	    	stmt.executeQuery("USE " + database);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//let me know if the interface changes by Sydney
	public Statement getStatement(){
		return stmt;
		
	}
	

}
