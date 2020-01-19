package com.revature.pojo;

import java.io.Serializable;

public class Offer implements Serializable {
	
	private Integer userId;
	private Integer carId;
	private Integer amount;
	private String status;
	private Integer id;
	private Integer numberOfMonths = 36;
	
	public Offer(Integer userId, Integer carId, Integer amount, Integer numberOfMonths) {
		this.userId = userId;
		this.carId = carId;
		this.amount = amount;
		this.numberOfMonths = numberOfMonths;
	}
	
	public Offer() {};
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getCarId() {
		return carId;
	}
	
	public void setCarId(Integer carId) {
		this.carId = carId;
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
