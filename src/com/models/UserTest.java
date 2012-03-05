package com.models; 

import static org.junit.Assert.*;

import org.junit.Test;


public class UserTest {

	@Test
	public void test() {
		User newUser = User.createUser("test", "blah");
		System.out.println(User.authenticateUser("test", "passwor"));
		System.out.println(User.authenticateUser("test", "password"));
		System.out.println(User.authenticateUser("test", "blah"));
	}

}
