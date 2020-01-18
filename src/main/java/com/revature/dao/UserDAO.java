package com.revature.dao;

import com.revature.users.User;

public interface UserDAO {
	
	public User authenticate(User user);
	
	public void logout();
	
	public void registerUser(User user);
	
}
