package com.revature.cars;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.dao.CarDAO;
import com.revature.dao.CarDAOPostgres;
import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class CarService {
	
	private ArrayList<Car> cars;
	private transient LoggerUtil logger;
	private CarDAO carDAO;
	private ArrayList<Car> currentUserCars;
	
	public CarService() {
		cars  = new ArrayList<Car>();
		logger = new LoggerUtil();
		carDAO = new CarDAOPostgres();
		setCurrentUserCars(new ArrayList<Car>());
	};
	
	public void addCar(Car car) throws SQLException {
		try {
			carDAO.addCar(car);
			logger.info("Car added to lot of id: " + car);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public int findCar(String carId) {
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getId().equals(carId)) {
				return i;
			}
		}
		return -1;
	}
	
	public Car getCar(Integer i) {
		return cars.get(i);
	}

	public ArrayList<Car> getCars() {
		return cars;
	}

	public ArrayList<Car> getCurrentUserCars() {
		return currentUserCars;
	}
	
	public ArrayList<Car> loadUserCars(Integer userId) throws SQLException {
		try {
			return carDAO.getUserCars(userId);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	public void loadCars() throws SQLException {
		cars = carDAO.getCars();
	}
	
	public void removeCar(Car car) throws SQLException {
		
		try {
			carDAO.removeCar(car.getId());
			logger.info("Car removed from lot: [" + car.getId() + "] " + car.getMake() + " " + car.getModel() + " " + car.getYear());
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	public Car removeCar(Integer id) throws SQLException {
		
		Car car = null;
		
		for (int i = 0; i < cars.size(); i++) {
			if (cars.get(i).getId().equals(id)) {
				car = cars.get(i);
				break;
			}
		}
		
		if (car == null) {
			System.out.println("Could not find a car by that id: ".toUpperCase() + id);
		} else {
			try {
				this.removeCar(car);
			} catch (SQLException e) {
				throw e;
			}
		}
		
		return car;
		
	}

	public void setCurrentUserCars(ArrayList<Car> currentUserCars) {
		this.currentUserCars = currentUserCars;
	}

}
