package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.pojo.Offer;
import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class OfferDAOPostgres implements OfferDAO {
	
	private static LoggerUtil logger = new LoggerUtil();

	@Override
	public void makeOffer(Offer offer) throws SQLException {
		
		String sql = "insert into offers (user_id, car_id, offer_amount, number_months) values (?,?,?,?)";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offer.getUser().getUserId());
			stmt.setInt(2, offer.getCar().getId());
			stmt.setInt(3, offer.getAmount());
			stmt.setInt(4, offer.getNumberOfMonths());
			
			int rowsUpdated = stmt.executeUpdate();
			
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
	public void acceptOffer(Offer offer) throws SQLException {
		
		String sql = "update offers set offer_status_id = 1 where offer_id = ? and offer_status_id != 2";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		Integer rowsUpdated = 0;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offer.getId());
			
			rowsUpdated = stmt.executeUpdate();
			
			if (rowsUpdated == 0) {
				System.out.println("The database was not able to find and accept the offer".toUpperCase());
			}
			
		} catch (SQLException e) {
			System.out.println("\nThere was an error accepting the offer".toUpperCase());
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		

		if (rowsUpdated == 1) {
			
			String sql_give_user_car = "update cars set user_id = ?, purchase_amount = ?, number_months = ? where car_id = ?";
			
			conn = ConnectionFactory.getConnection();
			
			PreparedStatement stmt2;
			
			try {
				stmt2 = conn.prepareStatement(sql_give_user_car);
				stmt2.setInt(1,offer.getUser().getUserId());
				stmt2.setInt(2, offer.getAmount());
				stmt2.setInt(3, offer.getNumberOfMonths());
				stmt2.setInt(4, offer.getCar().getId());
				
				rowsUpdated = stmt2.executeUpdate();
				
				if (rowsUpdated == 0) {
					System.out.println("\nThe offer was accepted, but the car was not able to be assigned to the purchaser".toUpperCase());
				}
				
			} catch (SQLException e) {
				System.out.println("\nThere was an error updating the car to the user".toUpperCase());
				throw e;
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.warn(e.toString());
				}
			}
			

			String sql_reject_other_offers = "update offers set offer_status_id = 2 where car_id = ? and offer_id != ? and offer_status_id != 1";
			
			conn = ConnectionFactory.getConnection();
			
			PreparedStatement stmt3;
			
			try {
				
				stmt3 = conn.prepareStatement(sql_reject_other_offers);
				stmt3.setInt(1, offer.getCar().getId());
				stmt3.setInt(2, offer.getId());
				
				stmt3.executeUpdate();
				
			} catch (SQLException e) {
				System.out.println("\nThere was an error rejecting the other offers on the car".toUpperCase());
				throw e;
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.warn(e.toString());
				}
			}
			
		}
		
	}

	@Override
	public void rejectOffer(Integer offerId) throws SQLException {

		String sql = "update offers set offer_status_id = 2 where offer_id = ? and offer_status_id != 1";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		Integer rowsUpdated = 0;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offerId);
			
			rowsUpdated = stmt.executeUpdate();
			
			if (rowsUpdated == 0) {
				System.out.println("\nThe database was not able to find and reject the offer".toUpperCase());
			}
			
		} catch (SQLException e) {
			System.out.println("\nThere was an error rejecting the offer".toUpperCase());
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
	public void cancelOffer(Integer offerId) throws SQLException {

		String sql = "delete from offers where offer_id = ? and offer_status_id != 1";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		Integer rowsUpdated = 0;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, offerId);
			
			rowsUpdated = stmt.executeUpdate();
			
			if (rowsUpdated == 0) {
				System.out.println("\nWe were not able to find and delete your offer".toUpperCase());
			}
			
		} catch (SQLException e) {
			System.out.println("\nThere was an error deleting your offer".toUpperCase());
			e.printStackTrace();
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
	public ArrayList<Offer> getOffers() throws SQLException {

		String sql = "select o.offer_id, o.user_id, o.car_id, o.offer_amount, o.number_months, u.first_name, u.last_name, c.make, c.model, c.model_year, c.price from offers as o inner join users as u on o.user_id = u.user_id inner join cars as c on o.car_id = c.car_id";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<Offer> offers = new ArrayList<Offer>();
		

		try {
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			Offer offer;
			User user;
			Car car;
			
			while(rs.next()) {
				offer = new Offer();
				user = new User();
				car = new Car();
				
				offer.setId(rs.getInt("offer_id"));
				user.setUserId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				offer.setUser(user);
				car.setId(rs.getInt("car_id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("model_year"));
				car.setPrice(rs.getInt("price"));
				offer.setCar(car);
				offer.setAmount(rs.getInt("offer_amount"));
				offer.setNumberOfMonths(rs.getInt("number_months"));
				
				offers.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		
		return offers;
		
	}

	@Override
	public ArrayList<Offer> getPendingOffers() throws SQLException {
		
		String sql = "select o.offer_id, o.user_id, o.car_id, o.offer_amount, o.number_months, u.first_name, u.last_name, c.make, c.model, c.model_year, c.price from offers as o inner join users as u on o.user_id = u.user_id inner join cars as c on o.car_id = c.car_id where o.offer_status_id = 0";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<Offer> offers = new ArrayList<Offer>();
		

		try {
			stmt = conn.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery();
			
			Offer offer;
			User user;
			Car car;
			
			while(rs.next()) {
				offer = new Offer();
				user = new User();
				car = new Car();
				
				offer.setId(rs.getInt("offer_id"));
				user.setUserId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				offer.setUser(user);
				car.setId(rs.getInt("car_id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("model_year"));
				car.setPrice(rs.getInt("price"));
				offer.setCar(car);
				offer.setAmount(rs.getInt("offer_amount"));
				offer.setNumberOfMonths(rs.getInt("number_months"));
				
				offers.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		
		return offers;
		
	}

	@Override
	public ArrayList<Offer> getUserOffers(Integer userId) throws SQLException {

		String sql = "select o.offer_id, o.user_id, o.car_id, o.offer_amount, o.number_months, c.make, c.model, c.model_year, c.price, os.offer_status from offers as o inner join cars as c on o.car_id = c.car_id inner join offer_statuses as os on o.offer_status_id = os.offer_status_id where o.user_id = ?";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement stmt;
		
		ArrayList<Offer> offers = new ArrayList<Offer>();
		

		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,userId);
			
			ResultSet rs = stmt.executeQuery();
			
			Offer offer;
			Car car;
			
			while(rs.next()) {
				offer = new Offer();
				car = new Car();
				
				offer.setId(rs.getInt("offer_id"));
				car.setId(rs.getInt("car_id"));
				car.setMake(rs.getString("make"));
				car.setModel(rs.getString("model"));
				car.setYear(rs.getInt("model_year"));
				car.setPrice(rs.getInt("price"));
				offer.setCar(car);
				offer.setAmount(rs.getInt("offer_amount"));
				offer.setNumberOfMonths(rs.getInt("number_months"));
				offer.setStatus(rs.getString("offer_status"));
				
				offers.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				logger.warn(e.toString());
			}
		}
		
		return offers;
		
	}

}
