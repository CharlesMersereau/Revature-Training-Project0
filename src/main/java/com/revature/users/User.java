package com.revature.users;

import java.util.ArrayList;

import com.revature.pojo.Car;

public class User {
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private int userRoleId;
	private int userId;
	protected ArrayList<ArrayList<String>> options = new ArrayList<ArrayList<String>>();
	private ArrayList<Car> myCars = new ArrayList<Car>();
	
	public User() {
		super();
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	public void setOptions(ArrayList<ArrayList<String>> options) {
		this.options = options;
	}
	
	public ArrayList<ArrayList<String>> getOptions() {
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

	public int getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(int userRoleId) {
		this.userRoleId = userRoleId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public ArrayList<Car> getMyCars() {
		return myCars;
	}

	public void addCar(Car car) {
		this.myCars.add(car);
	}
	
	public void setMyCars(ArrayList<Car> cars) {
		this.myCars = cars;
	}

}
