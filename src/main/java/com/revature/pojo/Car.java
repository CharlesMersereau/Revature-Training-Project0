package com.revature.pojo;

import java.io.Serializable;

public class Car implements Serializable {
	
	private String make;
	private String model;
	private String year;
	private String mileage;
	private String id;
	
	public Car(String make, String model, String year, String mileage) {
		super();
		this.make = make;
		this.model = model;
		this.year = year;
		this.mileage = mileage;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", " + year + " " + make.toUpperCase() + " " + model.toUpperCase() + " with " + mileage + " miles";
	}
	
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
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMileage() {
		return mileage;
	}
	
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
