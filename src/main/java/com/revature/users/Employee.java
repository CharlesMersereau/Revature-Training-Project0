package com.revature.users;

import java.util.ArrayList;

public class Employee extends User {
		
	public Employee() {
		super();
		this.setOptions();
	}

	public Employee(String username, String password) {
		super(username, password);
		this.setOptions();
	}
	
	private void setOptions() {
		options.add("'add' to add a car to the lot");
		options.add("'remove' to remove a car from the lot");
		options.add("'offers' to view all pending offers");
		options.add("'accept' to accept an offer");
		options.add("'reject' to reject an offer");
		options.add("'payments' to view all payments");
	}
}
