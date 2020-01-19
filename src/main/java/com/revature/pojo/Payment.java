package com.revature.pojo;

import java.io.Serializable;

import com.revature.util.LoggerUtil;

public class Payment implements Serializable {
	
	private transient LoggerUtil logger = new LoggerUtil();
	private String username;
	private Integer carId;
	private float amount;
	private int paymentsRemaining = 36;
	
	public Payment() {}

	public Payment(String username, Integer carId, int amount) {
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
	
	public Integer getCarId() {
		return carId;
	}
	
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int makePayment(float paymentAmount) {
		this.amount -= paymentAmount;
		this.paymentsRemaining -= 1;
		logger.info("Payment made by " + username + " in the amount of " + paymentAmount + " for " + carId);
		return this.paymentsRemaining;
	}
	
	public String getRemainingPayments() {
		String remaining = "" + this.paymentsRemaining + " remaining payments of " + String.format("$%,.2f", ((double)this.amount)/((double)this.paymentsRemaining)) + " for " + carId;
		return remaining;
	}
	
}
