package com.revature.users;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserServiceTest {
	
	private static UserService users;
	
	@Before
	public void setUp() throws Exception {
		users = new UserService();
	}

	@Test
	public void authenticateAttemptUserThatExistsCorrectly() {
		User user = new Customer("theodore","teddy");
		users.registerUser(user);
		assertTrue(users.authenticateUser(user) != null);
	}
	
	@Test
	public void authenticateAttemptUserThatExistsFailure() {
		User user1 = new Customer("marcus","morris");
		User user2 = new Customer("marcus","aurelius");
		users.registerUser(user1);
		assertTrue(users.authenticateUser(user2) == null);
	}
	
	@Test
	public void authenticateAttemptUserThatDoesNotExist() {
		User user1 = new Customer("michealScott","paper");
		User user2 = new Customer("dwightSchrute","paper");
		users.registerUser(user1);
		assertTrue(users.authenticateUser(user2) == null);
	}
	
	@Test
	public void testFindUserTrue() {
		User user = new Customer("michaelj","thegoat");
		users.registerUser(user);
		assertTrue(users.findUser("michaelj") > -1);
	}
	
	@Test
	public void testFindUserFalse() {
		User user = new Customer("michaelj","thegoat");
		users.registerUser(user);
		assertEquals(-1, users.findUser("kobe"));
	}
	
	@Test
	public void attemptToRegisterNewUser() {
		User user = new Customer("TomHanks","forrest");
		users.registerUser(user);
		assertTrue(users.findUser("TomHanks") > -1);
	}
	
	@Test
	public void attemptToReuseUsername() {
		User user = new Customer("brucewayne","normalguy");
		assertTrue(users.registerUser(user));
		user.setPassword("batman");
		assertFalse(users.registerUser(user));
	}
}
