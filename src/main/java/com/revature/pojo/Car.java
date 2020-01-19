package com.revature.pojo;

import java.io.Serializable;

public class Car implements Serializable {
	
	private String make;
	private String model;
	private Integer year;
	private Integer mileage;
	private Integer price;
	private Integer id;
	
	public Car(String make, String model, Integer year, Integer mileage, Integer price) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
		this.mileage = mileage;
		this.price = price;
	}
	
	public Car() {};
	
	public String getMake() {
		return make;
	}
	
	public void setMake(String make) {
		this.make = make;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public Integer getYear() {
		return year;
	}
	
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public Integer getMileage() {
		return mileage;
	}
	
	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}
