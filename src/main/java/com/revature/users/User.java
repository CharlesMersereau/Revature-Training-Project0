package com.revature.users;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.pojo.Car;

public class User implements Serializable {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int user_role_id;
	private int user_id;
	protected ArrayList<String> options = new ArrayList<String>();
	
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

	private void setOptions() {
		options.add("'lot' to view cars on the lot");
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getUser_role_id() {
		return user_role_id;
	}

	public void setUser_role_id(int user_role_id) {
		this.user_role_id = user_role_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

}
