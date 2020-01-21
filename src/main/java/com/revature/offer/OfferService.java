package com.revature.offer;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.cars.CarService;
import com.revature.dao.OfferDAO;
import com.revature.dao.OfferDAOPostgres;
import com.revature.pojo.Car;
import com.revature.pojo.Offer;
import com.revature.users.User;
import com.revature.util.LoggerUtil;

public class OfferService implements Serializable {

	private ArrayList<Offer> offers = new ArrayList<Offer>();
	private LoggerUtil logger = new LoggerUtil();
	private CarService lot = new CarService();
	private OfferDAO offerDAO = new OfferDAOPostgres();
	
	public void makeOffer(Offer offer) throws SQLException {
		
		try {
			offerDAO.makeOffer(offer);
			logger.info("An offer has been made with id " + offer.getId());
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	public void acceptOffer(Offer offer) throws SQLException {
		
		try {
			offerDAO.acceptOffer(offer);
			Car car = offer.getCar();
			User user = offer.getUser();
			logger.info(String.format("An offer has been accepted: [%d] $%,d for [%d] %d %s %s by [%d] %s %s", offer.getId(), offer.getAmount(), car.getId(), car.getYear(), car.getMake(), car.getModel(), user.getUserId(), user.getFirstName(), user.getLastName()));
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	public void rejectOffer(Offer offer) throws SQLException {
		
		try {
			offerDAO.rejectOffer(offer.getId());
			Car car = offer.getCar();
			User user = offer.getUser();
			logger.info(String.format("An offer has been rejected: [%d] $%,d for [%d] %d %s %s by [%d] %s %s", offer.getId(), offer.getAmount(), car.getId(), car.getYear(), car.getMake(), car.getModel(), user.getUserId(), user.getFirstName(), user.getLastName()));
		} catch (SQLException e) {
			throw e;
		}
		
	}
	
	public void cancelOffer(Offer offer) throws SQLException {

		try {
			offerDAO.cancelOffer(offer.getId());
			Car car = offer.getCar();
			User user = offer.getUser();
			logger.info(String.format("An offer has been deleted: [%d] $%,d for [%d] %d %s %s by [%d] %s %s", offer.getId(), offer.getAmount(), car.getId(), car.getYear(), car.getMake(), car.getModel(), user.getUserId(), user.getFirstName(), user.getLastName()));
		} catch (SQLException e) {
			throw e;
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
			if (offers.get(i).getUser().getUserId() == offer.getUser().getUserId() && offers.get(i).getCar().getId() == offer.getCar().getId()) {
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
	
	public ArrayList<Offer> getOffers() throws SQLException {
		
		try {
			offers = offerDAO.getOffers();
		} catch (SQLException e) {
			throw e;
		}
		
		return offers;
	}

	public ArrayList<Offer> getPendingOffers() throws SQLException {
		
		ArrayList<Offer> pendingOffers = new ArrayList<Offer>();
		
		try {
			pendingOffers = offerDAO.getPendingOffers();
		} catch (SQLException e) {
			throw e;
		}
		
		return pendingOffers;
	}
	
	public ArrayList<Offer> getUserOffers(Integer userId) throws SQLException {
		
		ArrayList<Offer> userOffers = new ArrayList<Offer>();
		
		try {
			userOffers = offerDAO.getUserOffers(userId);
		} catch (SQLException e) {
			throw e;
		}
		
		return userOffers;
	}
	
	public void rejectOffersOfRemovedCar(Integer carId) {
		for (int i = 0; i < offers.size(); i++) {
			if (offers.get(i).getCar().getId().equals(carId)) {
				try {
					rejectOffer(offers.get(i));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
		}
	}
	
 }
