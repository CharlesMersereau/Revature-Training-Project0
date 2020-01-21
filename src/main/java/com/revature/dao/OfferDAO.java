package com.revature.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.revature.pojo.Offer;

public interface OfferDAO {
	
	public void makeOffer(Offer offer) throws SQLException;
	
	public void acceptOffer(Offer offer) throws SQLException;
	
	public void rejectOffer(Integer offerId) throws SQLException;
	
	public void cancelOffer(Integer offerId) throws SQLException;
	
	public ArrayList<Offer> getOffers() throws SQLException;
	
	public ArrayList<Offer> getPendingOffers() throws SQLException;
	
	public ArrayList<Offer> getUserOffers(Integer userId) throws SQLException;
	

}
