package com.revature.lot;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class Lot {
	private ArrayList<Car> cars = new ArrayList<Car>();
	private LoggerUtil logger = new LoggerUtil();
	private int newIdNum = 0;
	
	public void addCar(Car car) {
		car.setId(this.createCarId(car));
		cars.add(car);
		logger.info("Car added to lot: " + car);
	}
	
	public String createCarId(Car car) {
		String id = null;
		id = "" + car.getMake().toUpperCase().charAt(0) 
				+ car.getModel().toUpperCase().charAt(0)
				+ newIdNum; 
		newIdNum++;
		return id;
	}
	

	public ArrayList<Car> getCars() {
		return cars;
	}
	
	public void loadCars() {
		this.addCar(new Car("Ford", "Expedition","2000","209,000"));
		this.addCar(new Car("Nissan", "Rogue", "2018"));
	}
	
	public void removeCar(Car car) {
		cars.remove(car);
		logger.info("Car removed from lot: " + car);
	}
	
	public void removeCar(String id) {
		Car car = null;
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getId().equals(id)) {
				car = cars.get(i);
				break;
			}
		}
		if (car == null) {
			System.out.println("Could not find a car by that id: " + id);
		} else {
			this.removeCar(car);
		}
	}

}
