package com.revature.offer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.revature.cars.CarService;
import com.revature.offer.OfferService;
import com.revature.pojo.Car;
import com.revature.pojo.Offer;

public class OfferServiceTest {
	
	private OfferService offers;
	private CarService lot;
	
	@Before
	public void setUp() {
		offers = new OfferService();
	}
	
	// tests for makeOffer
	//
	@Test
	public void makeGoodOffer() {
//		Offer newOffer = new Offer("bob","TT0", 5500);
//		offers.makeOffer(newOffer);
//		Offer madeOffer = offers.getOffers().get(0);
//		assertTrue(newOffer.getUsername().equals(madeOffer.getUsername()) && newOffer.getCarId().equals(madeOffer.getCarId()));
	}
	
	@Test
	public void madeOfferHasPendingStatus() {
//		Offer newOffer = new Offer("marywill","FF1", 3500);
//		offers.makeOffer(newOffer);
//		Offer madeOffer = offers.getOffer("MFF10");
//		assertTrue(madeOffer != null && madeOffer.getStatus().equals("Pending"));
	}
	
	// tests for acceptOffer
	//
	@Test
	public void acceptAnOffer() {
//		Offer newOffer = new Offer("steve","FM34",6500);
//		offers.makeOffer(newOffer);
//		offers.acceptOffer("SFM340");
//		assertTrue(offers.getOffer("SFM340").getStatus().equals("Accepted"));
	}
	
	@Test
	public void acceptingAnOfferRejectsOffersOnSameCar() {
//		Offer newOffer = new Offer("steve","FM34",6500);
//		Offer otherOffer = new Offer("bob","FM34",6200);
//		offers.makeOffer(newOffer);
//		offers.makeOffer(otherOffer);
//		offers.acceptOffer("SFM340");
//		assertTrue(offers.getOffer("BFM341").getStatus().equals("Rejected"));
	}

	@Test
	public void rejectedOfferCanNotBeAccepted() {
//		Offer newOffer = new Offer("steve","FM34",6500);
//		offers.makeOffer(newOffer);
//		offers.rejectOffer("SFM340");
//		assertFalse(offers.acceptOffer("SFM340"));
	}
	
	@Test
	public void acceptAnOfferThatDoesNotExist() {
//		assertFalse(offers.acceptOffer("344bao"));
	}
	
	// tests for rejectOffer
	//
	@Test
	public void rejectAnOffer() {
//		Offer newOffer = new Offer("steve","FM34",6500);
//		offers.makeOffer(newOffer);
//		offers.rejectOffer("SFM340");
//		assertTrue(offers.getOffer("SFM340").getStatus().equals("Rejected"));
	}
	
	@Test
	public void acceptedOfferCanNotBeRejected() {
//		Offer newOffer = new Offer("greg","TT100",900);
//		offers.makeOffer(newOffer);
//		offers.acceptOffer("GTT1000");
//		assertFalse(offers.rejectOffer("GTT1000"));
	}
	
	@Test
	public void rejectAnOfferThatDoesNotExist() {
//		assertFalse(offers.rejectOffer("56hf56"));
	}
	
	// tests for cancelOffer
	//
	@Test
	public void cancelOfferDeletesIt() {
//		Offer newOffer = new Offer("stephanie","FF13",3200);
//		offers.makeOffer(newOffer);
//		offers.cancelOffer("SFF130");
//		assertEquals(-1, offers.findOffer("SFF130"));
	}
	
	@Test
	public void cancelAnOfferThatDoesNotExist() {
//		assertFalse(offers.cancelOffer("12345"));
	}
}
