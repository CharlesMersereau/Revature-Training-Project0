package com.revature.offer;

import java.io.Serializable;
import java.util.ArrayList;

import com.revature.cars.CarService;
import com.revature.pojo.Offer;
import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class OfferService implements Serializable {

	private ArrayList<Offer> offers = new ArrayList<Offer>();
	private transient LoggerUtil logger = new LoggerUtil();
	private transient CarService lot = new CarService();
	private int newIdNum = 0;
	
	public void makeOffer(Offer offer) {
		
		if (this.findOffer(offer) == -1) {
			offer.setStatus("Pending");
			offers.add(offer);
			logger.info("An offer has been made with id " + offer.getId());
		} else {
			System.out.println("\nA second offer cannot be made on the same car. If you'd like to make a new offer, please cancel the old offer first.\n".toUpperCase());
		}
		
	}
	
	public boolean acceptOffer(Integer offerId) {
		
		int i = findOffer(offerId);
		
		if( i > -1) {
			
			Offer offer = offers.get(i); // potentially accepted offer
			
			if (offer.getStatus().equals("Rejected")) {
				logger.warn("Attempt to accept a rejected offer: " + offerId);
				System.out.println("\nYou can not accept an offer that has been rejected: ".toUpperCase() + offerId + "\n");
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
			System.out.println("\nAn offer matching this ID could not be found: ".toUpperCase() + offerId + "\n");
			return false;
		}
	}
	
	public boolean rejectOffer(Integer integer) {
		Offer offer = getOffer(integer);
		if(offer == null) {
			System.out.println("\nOffer with that ID can not be found: ".toUpperCase() + integer + "\n");
			return false;
		}
		if (!offer.getStatus().equals("Accepted")) {
			offer.setStatus("Rejected");
			logger.info("An offer has been rejected of id: " + integer);
			return true;
		} else {
			System.out.println("\nYou can not reject an offer that has been accepted: ".toUpperCase() + integer + "\n");
			return false;
		}
	}
	
	public boolean cancelOffer(Integer offerId) {
		int i = findOffer(offerId);
		
		if ( i > -1 ) {
			Offer temp = offers.get(i);
			if (!temp.getStatus().equals("Accepted")) {
				offers.remove(i);
				logger.info("An offer has been cancelled on car " + temp.getCarId());
				return true;
			} else {
				System.out.println("\nYou cannot cancel an offer once it has been accepted".toUpperCase());
				return false;
			}
			
		} else {
			System.out.println("\nOffer with that ID can not be found: ".toUpperCase() + offerId + "\n");
			return false;
		}
	}
	
	public Offer getOffer(Integer integer) {
		int i = findOffer(integer);
		if (i > -1) {
			return offers.get(i);
		} else {
			return null;
		}
		
	}
	
	public int findOffer(Offer offer) {
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getUserId().equals(offer.getUserId()) && offers.get(i).getCarId().equals(offer.getCarId())) {
				return i;
			} 
		}
		return -1;
	}
	
	public int findOffer(Integer offerId) {
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
			if (offers.get(i).getUserId().equals(username)) {
				userOffers.add(offers.get(i));
			} 
		}
		return userOffers;
	}
	
	public void rejectOffersOfRemovedCar(Integer carId) {
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getCarId().equals(carId)) {
				rejectOffer(offers.get(i).getId());
			} 
		}
	}
	
 }
