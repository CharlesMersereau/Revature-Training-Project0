package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOPostgres;
import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class UserService implements Serializable {
	
	private transient LoggerUtil logger;
	private ArrayList<User> users = new ArrayList<User>();
	private transient User currentUser;
	private transient UserDAO usersDAO;
	
	public UserService() {
		logger = new LoggerUtil();
		usersDAO = new UserDAOPostgres();
	}
	
	public User authenticateUser(User user) {
		currentUser = usersDAO.authenticate(user);
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
