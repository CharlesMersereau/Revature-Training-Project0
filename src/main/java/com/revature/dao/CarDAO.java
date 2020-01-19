package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.users.User;

public interface CarDAO {
	
	public void addCar(Car car) throws SQLException;
	
	public void removeCar(Integer id) throws SQLException;
	
	public ArrayList<Car> getCars() throws SQLException;
	
	public ArrayList<Car> getUserCars(Integer userId) throws SQLException;

}
