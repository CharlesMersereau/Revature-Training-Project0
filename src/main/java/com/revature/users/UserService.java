package com.revature.users;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOPostgres;
import com.revature.util.LoggerUtil;

public class UserService {
	
	private LoggerUtil logger;
	private User currentUser;
	private UserDAO userDAO;
	
	public UserService() {
		logger = new LoggerUtil();
		userDAO = new UserDAOPostgres();
		currentUser = null;
	}
	
	public User authenticateUser(User u) {
		User user = userDAO.authenticate(u);
		return user;
	}
	
	public void displayOptions() {
		
		ArrayList<ArrayList<String>> options = currentUser.getOptions();
		int i = 0;
		for (ArrayList<String> option : options) {
			System.out.println(String.format("[%d] " + option.get(1), ++i));
		}
		
	};
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public String interpretOption(Integer i) {
		if (i >= 0 && i <= currentUser.getOptions().size()) {
			return currentUser.getOptions().get(i-1).get(0);
		}
		return "try again";
	}
	
	public void loadUserOptions() {
		currentUser.setOptions(userDAO.loadUserOptions(currentUser));
	}

	public void logoutUser() {
		logger.info("System logged out of by: " + currentUser.getUsername());
		currentUser = null;
	}
	
	public void registerCustomer(User user) throws SQLException {
		
		try {
			userDAO.registerCustomer(user);
			logger.info("Added new user: " + user.getUsername());
			
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	public void setCurrentUser(User user) {
		currentUser = user;
	}

}
