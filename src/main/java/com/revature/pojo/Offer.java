package com.revature.pojo;

import java.io.Serializable;

public class Offer implements Serializable {
	
	private String username;
	private String carId;
	private Integer amount;
	private String status;
	private String id;
	
	public Offer(String username, String carId, Integer amount) {
		this.username = username;
		this.carId = carId;
		this.amount = amount;
	}
	
	public String getUsername() {
		return username;
	}
	
	@Override
	public String toString() {
		return  "ID: " + id + "  Status: " + status + ", from " + username + " for " + carId + " in the amount of " + amount;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
