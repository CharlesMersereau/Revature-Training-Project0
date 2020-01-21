package com.revature.payment;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.dao.PaymentDAO;
import com.revature.dao.PaymentDAOPostgres;
import com.revature.pojo.Car;
import com.revature.pojo.Payment;
import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class PaymentService {
	
	private PaymentDAO paymentDAO = new PaymentDAOPostgres();
	private LoggerUtil logger = new LoggerUtil();

	public ArrayList<Payment> getPayments() throws SQLException {
		
		ArrayList<Payment> payments = new ArrayList<Payment>();
		
		try {
			payments = paymentDAO.getPayments();
		} catch (SQLException e) {
			throw e;
		}
		
		return payments;
	}

	public void makePayment(Payment payment) throws SQLException {
		
		try{
			paymentDAO.makePayment(payment);
			User user = payment.getUser();
			Car car = payment.getCar();
			logger.info(String.format("%s %s has made a payment of $%,2f towards [%d] %d %s %s", user.getFirstName(), user.getLastName(), payment.getAmount(), car.getId(), car.getYear(), car.getMake(), car.getModel()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}
	
	public ArrayList<Payment> getRemainingPayments(User user) throws SQLException {
	
		ArrayList<Payment> userPayments = new ArrayList<Payment>();
		
		try {
			userPayments = paymentDAO.getRemainingPayments(user);
		} catch (SQLException e) {
			throw e;
		}
	
		return userPayments;
	}

}
