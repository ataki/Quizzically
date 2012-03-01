package com.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Author: Samir Patel
	
public class DBObject {
	private Statement stmt;
	private ResultSet rs;
	public DBObject() {
		
	}
	//added dummy Get() for statement so that I can pretend to communicate to DBObject for now.
	//let me know if the interface changes by Sydney
	public Statement getStatement(){
		return stmt;
		
	}
	public ResultSet setQuery(String Query){
		return null;
	}
	public ResultSet getResultSet(){
		return rs;
	}
}
