package com.revature.pojo;

import java.io.Serializable;

public class Payment implements Serializable {
	
	private String username;
	private String carId;
	private int amount;
	
	public Payment() {}

	public Payment(String username, String carId, int amount) {
		setUsername(username);
		setCarId(carId);
		setAmount(amount);
	}
	
	@Override
	public String toString() {
		return "Payment: " + username + " owes $" + amount + " for " +  carId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getCarId() {
		return carId;
	}
	
	public void setCarId(String carId) {
		this.carId = carId;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
}
