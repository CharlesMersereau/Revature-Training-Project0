package com.revature.payment;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.pojo.Payment;

public class PaymentService implements Serializable {
	
	private ArrayList<Payment> payments = new ArrayList<Payment>();

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public void addPayment(Payment payment) {
		payments.add(payment);
	}
	
	public Payment getPayment(String carId) {
		for (Payment payment : payments) {
			if (payment.getCarId().equals(carId)) {
				return payment;
			}
		}
		return null;
	}
	
	public ArrayList<Payment> getUserPayments(String username) {
		
		ArrayList<Payment> userPayments = new ArrayList<Payment>();
		
		for (int i = 0; i < payments.size(); i++) {
			if (payments.get(i).getUsername().equals(username)) {
				userPayments.add(payments.get(i));
			} 
		}
		return userPayments;
	}

}
