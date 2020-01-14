package com.revature.lot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class Lot implements Serializable {
	
	private ArrayList<Car> cars = new ArrayList<Car>();
	private transient LoggerUtil logger = new LoggerUtil();
	private int newIdNum = 0;
	
	public Lot() {};
	
	public void addCar(Car car) {
		car.setId(this.createCarId(car));
		cars.add(car);
		logger.info("Car added to lot: " + car);
	}
	
	public String createCarId(Car car) {
		String id = "";
		id = "" + car.getMake().toUpperCase().charAt(0) 
				+ car.getModel().toUpperCase().charAt(0)
				+ newIdNum; 
		newIdNum++;
		return id;
	}
	
	public int findCar(String carId) {
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getId().equals(carId)) {
				return i;
			}
		}
		return -1;
	}

	public ArrayList<Car> getCars() {
		return cars;
	}
	
	public void removeCar(Car car) {
		cars.remove(car);
		logger.info("Car removed from lot: " + car);
	}
	
	public Car removeCar(String id) {
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
		return car;
	}

}
