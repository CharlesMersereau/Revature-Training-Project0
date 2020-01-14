package com.revature.payment;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.revature.pojo.Payment;

public class PaymentServiceTest {
	
	private PaymentService payments;
	
	@Before
	public void setUp() {
		payments = new PaymentService();
	}

	// tests for add payment
	//
	@Test
	public void addAPayment() {
		Payment newPayment = new Payment("charlie","GGF23",5400);
		payments.addPayment(newPayment);
		ArrayList<Payment> all = payments.getPayments();
		assertEquals(1,all.size());
		assertEquals(all.get(0).getAmount(),5400);
	}
	
	// tests for getting a users payments
	@Test
	public void getPaymentsForUser() {
		Payment newPayment1 = new Payment("charlie","GGF23",5400);
		Payment newPayment2 = new Payment("charlie","GGF24",3333);
		payments.addPayment(newPayment1);
		payments.addPayment(newPayment2);
		ArrayList<Payment> userPayments = payments.getUserPayments("charlie");
		assertEquals(2,userPayments.size());
		assertTrue(userPayments.get(0).getUsername().equals("charlie") && userPayments.get(1).getUsername().equals("charlie"));
	}

}
