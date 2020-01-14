package com.revature.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.revature.payment.PaymentService;

public class PaymentServiceDAOSerialization implements PaymentServiceDAO {

	public void persistPaymentService(PaymentService paymentService) {
		String filename = "PaymentServiceDatabase.dat";
		
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(paymentService);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	public PaymentService readPaymentService() {

		String filename = "PaymentServiceDatabase.dat";

		PaymentService payments = null;
		
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try  {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);
			payments = (PaymentService) ois.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return payments;
	}
	
}
