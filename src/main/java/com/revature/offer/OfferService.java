package com.revature.offer;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.lot.Lot;
import com.revature.pojo.Offer;
import com.revature.util.LoggerUtil;

public class OfferService implements Serializable {

	private ArrayList<Offer> offers = new ArrayList<Offer>();
	private transient LoggerUtil logger = new LoggerUtil();
	private transient Lot lot = new Lot();
	private int newIdNum = 0;
	
	public void makeOffer(Offer offer) {
		
		if (this.findOffer(offer) == -1) {
			offer.setId(this.createId(offer));
			offer.setStatus("Pending");
			offers.add(offer);
			logger.info("an offer has been made with id " + offer.getId());
		} else {
			System.out.println("A second offer cannot be made on the same car. If you'd like to make a new offer, please cancel the old offer first.\n");
		}
		
	}
	
	public boolean acceptOffer(String offerId) {
		
		int i = findOffer(offerId);
		
		if( i > -1) {
			
			Offer offer = offers.get(i); // potentially accepted offer
			
			if (offer.getStatus().equals("Rejected")) {
				logger.warn("Attempt to accept a rejected offer " + offerId);
				System.out.println("You can not accept an offer that has been rejected: " + offerId + "\n");
				return false;
			} else {
				offer.setStatus("Accepted");
				logger.info("An offer has been accepted of id: " + offerId);
				
				for(int j = 0; j < offers.size(); j++) {
					if (offers.get(j).getCarId().equals(offer.getCarId())) {
						if (!offers.get(j).getStatus().equals("Accepted")) {
							rejectOffer(offers.get(j).getId());
						}
					}
				}
				
				return true;
			}
			
		} else {
			System.out.println("An offer mathcing this ID could not be found: " + offerId + "\n");
			return false;
		}
	}
	
	public boolean rejectOffer(String offerId) {
		Offer offer = getOffer(offerId);
		if(offer == null) {
			System.out.println("Offer with that ID can not be found: " + offerId + "\n");
			return false;
		}
		if (!offer.getStatus().equals("Accepted")) {
			offer.setStatus("Rejected");
			logger.info("An offer has been rejected of id: " + offerId);
			return true;
		} else {
			System.out.println("You can not reject an offer that has been accepted: " + offerId + "\n");
			return false;
		}
	}
	
	public boolean cancelOffer(String offerId) {
		int i = findOffer(offerId);
		
		if ( i > -1 ) {
			Offer temp = offers.get(i);
			offers.remove(i);
			logger.info("An offer has been cancelled on car " + temp.getCarId());
			return true;
		} else {
			System.out.println("Offer with that ID can not be found: " + offerId + "\n");
			return false;
		}
	}
	
	private String createId(Offer offer) {
		String id = "";
		id = "" + offer.getUsername().toUpperCase().charAt(0)
				+ offer.getCarId()
				+ newIdNum;
		newIdNum++;
		return id;
	}
	
	public Offer getOffer(String offerId) {
		int i = findOffer(offerId);
		if (i > -1) {
			return offers.get(i);
		} else {
			return null;
		}
		
	}
	
	public int findOffer(Offer offer) {
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getUsername().equals(offer.getUsername()) && offers.get(i).getCarId().equals(offer.getCarId())) {
				return i;
			} 
		}
		return -1;
	}
	
	public int findOffer(String offerId) {
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getId().equals(offerId)) {
				return i;
			} 
		}
		return -1;
	}
	
	public ArrayList<Offer> getOffers() {
		return offers;
	}

	public ArrayList<Offer> getPendingOffers() {
		
		ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
		
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getStatus().equals("Pending")) {
				pendingOffers.add(offers.get(i));
			} 
		}
		return pendingOffers;
	}
	
	public ArrayList<Offer> getUserOffers(String username) {

		ArrayList<Offer> userOffers = new ArrayList<Offer>();
		
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getUsername().equals(username)) {
				userOffers.add(offers.get(i));
			} 
		}
		return userOffers;
	}
	
	public void rejectOffersOfRemovedCar(String carId) {
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getCarId().equals(carId)) {
				rejectOffer(offers.get(i).getId());
			} 
		}
	}
	
 }
