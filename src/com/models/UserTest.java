package com.models; 

import static org.junit.Assert.*;

import org.junit.Test;


public class UserTest {

	@Test
	public void test() {
		User newUser1 = User.createUser("test", "test@test.com", "blah");
		User newUser2 = User.createUser("test2", "test@test.com", "blah");
		User newUser3 = User.createUser("Samir", "test@test.com", "blah");
		System.out.println(User.authenticateUser("test", "passwor"));
		System.out.println(User.authenticateUser("test", "password"));
		System.out.println(User.authenticateUser("test", "blah"));
	}

}
