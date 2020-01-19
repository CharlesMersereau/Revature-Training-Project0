package com.revature.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class CarDAOPostgres implements CarDAO {
	
	private static LoggerUtil logger = new LoggerUtil();

	@Override
	public void addCar(Car c) throws SQLException {

		String sql = "insert into cars (make,model,model_year,mileage,price) values ('" + c.getMake() + "','" + c.getModel() + "'," + c.getYear() + "," + c.getMileage() + "," + c.getPrice() + ")";                     
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			
			int rowsUpdated = stmt.executeUpdate(sql);
			
	        if(rowsUpdated == 0) {
	        	System.out.println("\nThe system was not able to add the car to the database".toUpperCase());
	        }
	        
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
        
	}

	@Override
	public void removeCar(Integer id) throws SQLException {
		
		String sql = "delete from cars where car_id = " + id;                     
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		
		try {
			stmt = conn.createStatement();
			
			int rowsUpdated = stmt.executeUpdate(sql);
			
	        if(rowsUpdated == 0) {
	        	System.out.println("\nThere is no car in the database with that id".toUpperCase());
	        }
	        
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		
	}

	@Override
	public ArrayList<Car> getCars() throws SQLException {
		
		String sql = "select car_id,make,model,model_year,mileage,price from cars where user_id is NULL";                     
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		ArrayList<Car> cars = new ArrayList<Car>();
		
		try {
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Car car = new Car();
				Integer id = rs.getInt("car_id");
				car.setId(id);
		        car.setMake(rs.getString("make"));
		        car.setModel(rs.getString("model"));
		        car.setYear(rs.getInt("model_year"));
		        car.setMileage(rs.getInt("mileage"));
		        car.setPrice(rs.getInt("price"));
		        
		        cars.add(car);
			}
	        
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		return cars;
	}

	@Override
	public ArrayList<Car> getUserCars(Integer userId) throws SQLException {
		
		String sql = "select car_id,make,model,model_year,mileage,price from cars where user_id = " + userId;                     
		Connection conn = ConnectionFactory.getConnection();
		Statement stmt;
		ArrayList<Car> cars = new ArrayList<Car>();
		
		try {
			stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				Car car = new Car();
				Integer id = rs.getInt("car_id");
				car.setId(id);
		        car.setMake(rs.getString("make"));
		        car.setModel(rs.getString("model"));
		        car.setYear(rs.getInt("model_year"));
		        car.setMileage(rs.getInt("mileage"));
		        car.setPrice(rs.getInt("price"));
		        
		        cars.add(car);
			}
	        
		} catch (SQLException e) {
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		return cars;
	}

}
