package com.tests; 

import static org.junit.Assert.*;

import org.junit.Test;

import com.models.User;


public class UserTest {

	/**
	 * Creates new users; these insert calls have already been called and
	 * tested so no need to call again !
	 * 
	 * TESTS PASS FRI 3/9
	 */
	
	@Test
	public void test1() {
		// Previously called arleay
//		User newUser1 = User.createUser("sydney", "test@test.com", "123");
//		User newUser2 = User.createUser("chris", "test@test.com", "123");
//		User newUser3 = User.createUser("samir", "test@test.com", "123");
//		User newUser4 = User.createUser("jim", "test@test.com", "123");
		
		
//		System.out.println(User.authenticateUser("sydney", "asdf"));
//		System.out.println(User.authenticateUser("sydney", "password"));
//		System.out.println(User.authenticateUser("sydney", "blah"));
//		System.out.println(User.authenticateUser("sydney", "123"));
		
		
		/*
		 * Created 10 mock users for Sydneys testing purposes
		for(int i = 0 ; i < 10; i++) {
			User newUser  = User.createUser("user" + i, "test@test.com", "123");
		}*/
	}
	
	@Test
	public void testRetrieval() {
		User test = new User("Samir");
		System.out.println("Name: " + test.getName() + " Email: " + test.getEmail() + " Admin: " + test.isAdmin());
		test.setAdmin(false);
		test.setEmail("samir42@stanford.edu");
		System.out.println("Done setting");
		System.out.println("Name: " + test.getName() + " Email: " + test.getEmail() + " Admin: " + test.isAdmin());
		System.out.println("Syncing");
		test.sync();
		System.out.println("Name: " + test.getName() + " Email: " + test.getEmail() + " Admin: " + test.isAdmin());

		System.out.println();

		test = new User("Samir");
		System.out.println("Name: " + test.getName() + " Email: " + test.getEmail() + " Admin: " + test.isAdmin());
		test.setAdmin(true);
		test.setEmail("samir@arxia.org");
		System.out.println("Done setting");
		System.out.println("Name: " + test.getName() + " Email: " + test.getEmail() + " Admin: " + test.isAdmin());
		System.out.println("Syncing");
		test.sync();
		System.out.println("Name: " + test.getName() + " Email: " + test.getEmail() + " Admin: " + test.isAdmin());
	}
	

}
