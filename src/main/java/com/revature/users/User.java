package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	
	private String username;
	private String password;
	protected ArrayList<String> options = new ArrayList<String>();
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public User() {
		super();
		this.setOptions();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
		this.setOptions();
	}
	
	private void setOptions() {
		options.add("'lot' to view cars on the lot");
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}
}
