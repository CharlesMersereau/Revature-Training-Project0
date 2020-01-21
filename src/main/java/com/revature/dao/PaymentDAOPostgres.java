package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.pojo.Payment;
import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class PaymentDAOPostgres implements PaymentDAO {
	
	private LoggerUtil logger = new LoggerUtil();

	@Override
	public void makePayment(Payment payment) throws SQLException {

		String sql = "insert into payments (car_id, amount) values (?,?)";                     
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, payment.getCar().getId());
			stmt.setFloat(2, payment.getAmount());
			
			int rowsUpdated = stmt.executeUpdate();
			
	        if(rowsUpdated == 0) {
	        	System.out.println("\nYour payment was not successful".toUpperCase());
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
	public ArrayList<Payment> getPayments() {
		
		String sql = "select p.car_id, p.amount, c.make, c.model, c.model_year, u.first_name, u.last_name from payments as p inner join cars as c on p.car_id = c.car_id inner join users as u on c.user_id = u.user_id";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<Payment> payments = new ArrayList<Payment>();
		Car car;
		User user;
		
		try {
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Payment payment = new Payment();
				
				car = new Car();
				car.setId(rs.getInt("car_id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("model_year"));
				payment.setCar(car);
				user = new User();
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				payment.setUser(user);
				payment.setAmount(rs.getInt("amount"));
				payment.setUser(user);
		        
				payments.add(payment);
			}
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
        
		return payments;
	}

	@Override
	public ArrayList<Payment> getRemainingPayments(User user) {
		
		String sql = "select p.car_id, p.amount, c.make, c.model, c.model_year, c.purchase_amount, c.number_months, c.paid_off, c.user_id from payments as p inner join cars as c on p.car_id = c.car_id where c.user_id = ? and c.paid_off = false";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<Payment> payments = new ArrayList<Payment>();
		Car car;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user.getUserId());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				
				Payment payment = new Payment();
				car = new Car();

				car.setId(rs.getInt("car_id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("model_year"));
				car.setPurchaseAmount(rs.getInt("purchase_amount"));
				car.setNumberOfMonths(rs.getInt("number_months"));
				car.setPaidOff(rs.getBoolean("paid_off"));
				payment.setCar(car);
				payment.setAmount(rs.getFloat("amount"));
				payment.setUser(user);
		        
				payments.add(payment);
			}
	        
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
        
		return payments;
		
	}

	
}
