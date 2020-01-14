package com.revature.dao;

import com.revature.payment.PaymentService;

public interface PaymentServiceDAO {

	public void persistPaymentService(PaymentService paymentService);
	
	public PaymentService readPaymentService();
}
