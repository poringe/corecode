package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.Farm;

public interface FarmRepository {

	Farm find(String farmCode);

	List<Farm> findAll(Integer firstResult, Integer maxResult);

	List<Farm> findByFarmProfileCode(String farmProfileCode, Integer firstResult, Integer maxResult);
	
	void create(Farm farm);

	Farm update(Farm farm);

	void delete(String farmCode);
	
	List<Farm> filter(String searchText, String farmProfileCode, Integer firstResult, Integer maxResult);
	
	String codeGenerator(String farmProfileCode);
}
