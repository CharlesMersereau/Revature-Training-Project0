package com.revature.users;

import java.util.ArrayList;

import com.revature.pojo.Car;

public class Customer extends User {
	
	private ArrayList<Car> myCars = new ArrayList<Car>();
	
	public Customer() {
		super();
		this.setOptions();
	}

	public Customer(String username, String password) {
		super(username, password);
		this.setOptions();
	}
	
	private void setOptions() {
		options.add("'make offer' to make an offer on a car");
		options.add("'my offers' to view your current offers");
		options.add("'cancel offer' to cancel a current offer (recommended for rejected offers)");
		options.add("'my cars' to view your cars");
		options.add("'my payments' to view your remaining payments");
		options.add("'make payment' to make a payment");
	}
	
	public ArrayList<String> getOptions() {
		return options;
	}

	public ArrayList<Car> getMyCars() {
		return myCars;
	}

	public void addCar(Car car) {
		this.myCars.add(car);
	}
}
