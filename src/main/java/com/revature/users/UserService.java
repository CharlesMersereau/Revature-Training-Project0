package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class UserService implements Serializable {
	
	private transient LoggerUtil logger;
	private ArrayList<User> users = new ArrayList<User>();
	private transient User currentUser;
	
	public UserService() {
		logger = new LoggerUtil();
	}
	
	public User authenticateUser(User user) {
		
		int i = findUser(user.getUsername());
		
		if (i > -1) {
			if (user.getPassword().equals(users.get(i).getPassword())) {
				currentUser = users.get(i);
				return currentUser;
			} else {
				System.out.println("You have entered the incorrect password for this account.");
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
	
	public void addEmployee(String username, String password) {
		users.add(new Employee(username,password));
		logger.info("added new employee: " + username);
	}

	public void logoutUser() {
		currentUser = null;
	}
	
	public boolean registerUser(User user) {
		
		
		if (this.findUser(user.getUsername()) == -1) {
			
			User customer = new Customer(user.getUsername(), user.getPassword());
			users.add(customer);
			logger.info("added new user: " + customer.getUsername());
			return true;
			
		} else {
			
			System.out.println("Sorry, but this username has already been taken.");
			logger.debug("attempted to register a user using a username already in use: " + user.getUsername());
			return false;
			
		}
		
	}

	public void transferCarToUser(Car car, String username) {
		((Customer)users.get(findUser(username))).addCar(car);
	}
}
