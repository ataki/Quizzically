package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.backend.DBObject;
/**
 * A tag is something used in 
 * addition to categories to provide
 * another level of association.
 * These are user-generated. 
 * 
 * Tags are associated with both user and id,
 * and so keep track of both (a bit wasteful)
 * 
 * We also have a Quiz_tag_quiz Many-To-Many relationship
 * between quiz and tags. This means that whenever we insert
 * a tag or quiz, make sure to update this relations table so
 * that we can refer to it when necessary;
 * 
 * Database object:
 * 
	+---------+--------------+------+-----+---------+----------------+
	| Field   | Type         | Null | Key | Default | Extra          |
	+---------+--------------+------+-----+---------+----------------+
	| id      | int(11)      | NO   | PRI | NULL    | auto_increment |
	| user_id | int(11)      | NO   | MUL | NULL    |                |
	| tag     | varchar(130) | NO   |     | NULL    |                |
	+---------+--------------+------+-----+---------+----------------+
 
 * @author jimzheng
 *
 */

public class Tag {
	public int id; // could be user or quiz
	public int quiz_id;
	public int user_id;
	public String tag;
	
	public Tag(int id, int quiz_id, int user_id, String tag) {
		this.id = id;
		this.tag = tag;
	}
	
	
	
}
