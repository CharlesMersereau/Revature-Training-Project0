package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.dao.DBService;
import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class UserService implements Serializable {
	
	private transient LoggerUtil logger;
	private ArrayList<User> users = new ArrayList<User>();
	private transient User currentUser;
	private transient DBService db = new DBService();
	
	public UserService() {
		logger = new LoggerUtil();
	}
	
	public User authenticateUser(User user) {
		db = new DBService();
		currentUser = db.login(user.getUsername(), user.getPassword());
		
//		int i = findUser(user.getUsername());
//		
//		if (i > -1) {
//			if (user.getPassword().equals(users.get(i).getPassword())) {
//				currentUser = users.get(i);
//				logger.info("System logged into by: " + currentUser.getUsername());
//				return currentUser;
//			} else {
//				System.out.println("\nYou have entered the incorrect password for this account.");
//			}
//		} else {
//			System.out.println("\nWe were unable to locate an account with that username: " + user.getUsername());
//		}
		
		return currentUser;
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
		logger.info("Added new employee: " + username);
	}

	public void logoutUser() {
		logger.info("System logged out of by: " + currentUser.getUsername());
		currentUser = null;
	}
	
	public boolean registerUser(User user) {
		
		
		if (this.findUser(user.getUsername()) == -1) {
			
			User customer = new Customer(user.getUsername(), user.getPassword());
			users.add(customer);
			logger.info("Added new user: " + customer.getUsername());
			return true;
			
		} else {
			
			System.out.println("\nSorry, but this username has already been taken.".toUpperCase());
			return false;
			
		}
		
	}

	public void transferCarToUser(Car car, String username) {
		((Customer)users.get(findUser(username))).addCar(car);
	}
}
