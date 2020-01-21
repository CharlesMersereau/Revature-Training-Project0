package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.pojo.Payment;
import com.revature.users.User;

public interface PaymentDAO {

	public void makePayment(Payment payment) throws SQLException;
	
	public ArrayList<Payment> getPayments() throws SQLException;
	
	public ArrayList<Payment> getRemainingPayments(User user) throws SQLException;
	
}
