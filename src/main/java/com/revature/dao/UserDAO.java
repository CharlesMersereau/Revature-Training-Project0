package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.users.User;

public interface UserDAO {
	
	public User authenticate(User user);
	
	public ArrayList<ArrayList<String>> loadUserOptions(User user);
	
	public void registerCustomer(User user) throws SQLException;
	
}
