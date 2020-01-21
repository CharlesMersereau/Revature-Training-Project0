package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.util.LoggerUtil;

public class CarDAOPostgres implements CarDAO {
	
	private static LoggerUtil logger = new LoggerUtil();

	@Override
	public void addCar(Car c) throws SQLException {

		String sql = "insert into cars (make,model,model_year,mileage,price) values (?,?,?,?,?)";                     
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, c.getMake());
			stmt.setString(2, c.getModel());
			stmt.setInt(3, c.getYear());
			stmt.setInt(4, c.getMileage());
			stmt.setInt(5, c.getPrice());
			
			int rowsUpdated = stmt.executeUpdate();
			
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
	public void removeCar(Integer carId) throws SQLException {
		
		String sql = "delete from cars where car_id = ?";                     
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, carId);
			
			int rowsUpdated = stmt.executeUpdate();
			
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
		
		PreparedStatement stmt;
		
		ArrayList<Car> cars = new ArrayList<Car>();
		
		try {
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				Car car = new Car();
				car.setId(rs.getInt("car_id"));
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
		
		String sql = "select car_id,make,model,model_year,mileage,price from cars where user_id = ?";                     
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<Car> cars = new ArrayList<Car>();
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			
			ResultSet rs = stmt.executeQuery();
			
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
