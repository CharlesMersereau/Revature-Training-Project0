package com.revature.users;

import java.util.ArrayList;

import com.revature.pojo.Car;

public class Customer extends User {
	
	ArrayList<Car> myCars = new ArrayList<Car>();
	
	public Customer() {
		super();
		this.setOptions();
	}

	public Customer(String username, String password) {
		super(username, password);
		this.setOptions();
	}
	
	private void setOptions() {
		options.add("'make' to make an offer on a car");
		options.add("'cancel' to cancel a current offer (recommended for rejected offers)");
		options.add("'cars' to view your cars");
		options.add("'my payments' to view your remaining payments");		
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}
}
