package com.revature.dao;

import java.sql.SQLException;

import com.revature.users.User;

public interface UserDAO {
	
	public User authenticate(User user);
	
	public void registerCustomer(User user) throws SQLException;
	
}
