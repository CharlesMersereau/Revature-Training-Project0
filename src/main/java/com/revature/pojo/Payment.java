package com.revature.pojo;

import java.io.Serializable;

import com.revature.util.LoggerUtil;

public class Payment implements Serializable {
	
	private transient LoggerUtil logger = new LoggerUtil();
	private String username;
	private String carId;
	private int amount;
	private int paymentsRemaining = 36;
	
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
	
	public int makePayment(int paymentAmount) {
		this.amount -= paymentAmount;
		this.paymentsRemaining--;
		logger.info("Payment made by " + username + " in the amount of " + paymentAmount + " for " + carId);
		return this.paymentsRemaining;
	}
	
	public String getRemainingPayments() {
		String remaining = "" + this.paymentsRemaining + " remaining payments of " + String.format("$%,.2f", ((double)this.amount)/((double)this.paymentsRemaining)) + " for " + carId;
		return remaining;
	}
	
}
