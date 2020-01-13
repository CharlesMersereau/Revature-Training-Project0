package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.util.LoggerUtil;

public class UserService implements Serializable {
	
	private LoggerUtil logger = new LoggerUtil();
	private ArrayList<User> users = new ArrayList<User>();
	
	public void loadUsers() {
		
	}
	
	public boolean registerUser(User user) {
		if (this.findUser(user.getUsername()) == -1) {
			users.add(user);
			logger.info("added new user: " + user.getUsername());
			return true;
		} else {
			System.out.println("Sorry, but this username has already been taken.");
			logger.error("attempted to register a user using a username already in use: " + user.getUsername());
			return false;
		}
		
	}
	
	public User authenticateUser(User user) {
		int i = findUser(user.getUsername());
		if (i > -1) {
			if (user.getPassword().contentEquals(users.get(i).getPassword())) {
				return users.get(i);
			} else {
				System.out.println("Sorry, but you have entered the incorrect password.");
			}
		} else {
			System.out.println("We were unable to locate an account with that username: " + user.getUsername());
		}
		return null;
	}

	public int findUser(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {
				return i;
			}
		}
		return -1;
	}
}
