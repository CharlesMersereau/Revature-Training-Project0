package com.revature.dao;

import com.revature.offer.OfferService;

public interface OfferServiceDAO {
	
	public void persistOfferService(OfferService offerService);
	
	public OfferService readOfferService();

}
