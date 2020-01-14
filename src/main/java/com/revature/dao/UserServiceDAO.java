package com.revature.dao;

import com.revature.users.UserService;

public interface UserServiceDAO {

	public void persistUserService(UserService userService);
	
	public UserService readUserService();
	
}
