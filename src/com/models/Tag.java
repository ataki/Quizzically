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
 * The reason we keep so much about tags is that
 * we can now have a way of displaying 'hits'
 * in our site using tags. 
 * 
 * In the future these will become a new object.
 * 
 * Database object:
 * 
 * +----------------------------+
	| Tag                        |
	+----------------------------+
	| UNIQUE ID                  |
	| FOREIGN KEY USERID         |
	| FOREIGN KEY QUIZID         |
	| CHARFIELD TAG MAXLENGTH=30 |
	+----------------------------+
 * @author jimzheng
 *
 */
public class Tag {
	public int id; // could be user or quiz
	public String tag;
	
	public Tag(int id, String tag) {
		this.id = id;
		this.tag = tag;
	}
}
