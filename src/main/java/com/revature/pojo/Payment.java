package com.revature.pojo;

import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class Payment {
	
	private LoggerUtil logger = new LoggerUtil();
	private User user;
	private Car car;
	private float amount;
	
	public Payment() {}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
}
