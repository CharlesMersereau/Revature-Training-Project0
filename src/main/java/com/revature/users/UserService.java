package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.util.LoggerUtil;

public class UserService implements Serializable {
	
	private LoggerUtil logger = new LoggerUtil();
	private ArrayList<User> users = new ArrayList<User>();
	private User currentUser;
	
	public User authenticateUser(User user) {
		
		int i = findUser(user.getUsername());
		
		if (i > -1) {
			if (user.getPassword().equals(users.get(i).getPassword())) {
				currentUser = users.get(i);
				return currentUser;
			} else {
				System.out.println("Sorry, but you have entered the incorrect password.");
			}
		} else {
			System.out.println("We were unable to locate an account with that username: " + user.getUsername());
		}
		
		return null;
	}
	
	public void displayOptions() {
		
		ArrayList<String> options = currentUser.getOptions();
		
		for (String option : options) {
			System.out.println(option);
		}
	};

	public int findUser(String username) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUsername().equals(username)) {	
				return i;
			}
		}
		return -1;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void loadUsers() {
		users.add(new Employee("chuck","pass"));
	}

	public void logoutUser() {
		currentUser = null;
	}
	
	public boolean registerUser(User user) {
		
		if (this.findUser(user.getUsername()) == -1) {
			
			users.add(new Customer(user.getUsername(),user.getPassword()));
			logger.info("added new user: " + user.getUsername());
			return true;
			
		} else {
			
			System.out.println("Sorry, but this username has already been taken.");
			logger.error("attempted to register a user using a username already in use: " + user.getUsername());
			return false;
			
		}
		
	}
}
