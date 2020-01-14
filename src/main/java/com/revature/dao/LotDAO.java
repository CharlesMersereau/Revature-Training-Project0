package com.revature.dao;

import com.revature.lot.Lot;

public interface LotDAO {
	
	public void persistLot(Lot lot);
	
	public Lot readLot();

}
