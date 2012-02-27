package misc;
/** Author: Jim 
 * 
 * Note: Tested below. I'm sure this is 
 * 	pretty heavily tested all the time, lol.
 * 
 * GSON is Google's proprietary solution for
 * JSON communication. It maintains OOP by
 * magically transforming JSON strings into 
 * objects and back, so long as we have the right 
 * fields. 
 * 
 * Note: JSON must be strictly well-defined, otherwise
 * GSON will throw an exception and return nothing.
 */

import com.google.gson.*;

public class GSONWrapper {

	public GSONWrapper() { /* nothing */ }
	
	public class User {
		public String alias;
		public String first;
		public String last;
		public String password;
		
		public User(String alias, String first, String last, String password) {
			this.alias = alias;
			this.first = first;
			this.last = last;
			this.password = password;
		}
		
		public String toString() {
			return alias + ":(" + first + " " + last + ")" + "pw: " + password;
		}
	}
	
	/* 
	 * some standard tests tests to make sure
	 * we're working correctly
	 */
	public static void main(String args[]) {
		GSONWrapper wrapper = new GSONWrapper();
		Gson gs = new Gson(); 
		// set up a new user,
		// and JSON string to
		// represent another user
		User user = wrapper.new User("jim32990", "Jim", "Zheng", "xyto23e");
		
		// Note: String must be well-defined
		String JSONofUser = "{" +
						  "\"alias\":\"cowabunga23\"," +
						  "\"first\":\"William\"," +
						  "\"last\":\"Johnson\"," +
						  "\"password\":\"secrettechnique\"" + 
						  "}";
		
		System.out.println(JSONofUser);
		// STRING TO OBJECT CLASS
		String json = gs.toJson(user);
		System.out.println(json);
		
		// OBJECT CLASS TO STRING
		User obj = gs.fromJson(JSONofUser, User.class);
		System.out.println(obj);
	}
}
