package com.revature.users;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOPostgres;
import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class UserService implements Serializable {
	
	private transient LoggerUtil logger;
	private ArrayList<User> users = new ArrayList<User>();
	private transient User currentUser;
	private transient UserDAO userDAO;
	
	public UserService() {
		logger = new LoggerUtil();
		userDAO = new UserDAOPostgres();
	}
	
	public User authenticateUser(User u) {
		User user = userDAO.authenticate(u);
		return user;
	}
	
	public void displayOptions() {
		
		ArrayList<String> options = currentUser.getOptions();
		
		for (String option : options) {
			System.out.println(option);
		}
	};
	
	public User getCurrentUser() {
		return currentUser;
	}

	public void logoutUser() {
		logger.info("System logged out of by: " + currentUser.getUsername());
		currentUser = null;
	}
	
	public void registerCustomer(Customer customer) throws SQLException {
		
		try {
			userDAO.registerCustomer(customer);
			logger.info("Added new user: " + customer.getUsername());
			
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}

}
