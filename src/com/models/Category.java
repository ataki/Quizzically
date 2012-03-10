package com.models;

public class Category {
	
	/*
	+-------+--------------+------+-----+---------+----------------+
	| Field | Type         | Null | Key | Default | Extra          |
	+-------+--------------+------+-----+---------+----------------+
	| id    | int(11)      | NO   | PRI | NULL    | auto_increment |
	| name  | varchar(130) | NO   |     | NULL    |                |
	+-------+--------------+------+-----+---------+----------------+
	 */
	public String name;
	public int quiz_id;
	public Category(String name, int quiz_id) {
		this.name = name;
		this.quiz_id = quiz_id;
	}
}
