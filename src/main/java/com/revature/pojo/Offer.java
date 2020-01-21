package com.revature.pojo;

import com.revature.users.User;

public class Offer {
	
	private User user;
	private Car car;
	private Integer amount;
	private String status;
	private Integer id;
	private Integer numberOfMonths = 36;
	
	public Offer() {};
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Car getCar() {
		return car;
	}
	
	public void setCar(Car car) {
		this.car = car;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumberOfMonths() {
		return numberOfMonths;
	}

	public void setNumberOfMonths(Integer numberOfMonths) {
		this.numberOfMonths = numberOfMonths;
	}

}
