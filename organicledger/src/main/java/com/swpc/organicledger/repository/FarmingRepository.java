package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.Farming;

public interface FarmingRepository {

	Farming find(String farmingCode);

	List<Farming> findAll(Integer firstResult, Integer maxResult);

	List<Farming> findByFarmingProfileCode(String farmProfileCode, Integer firstResult, Integer maxResult);
	
	void create(Farming farming);

	Farming update(Farming farming);

	void delete(String farmingCode);
	
	List<Farming> filter(String searchText, String farmProfileCode, Integer firstResult, Integer maxResult);
	
	String codeGenerator(String farmCode);
}
