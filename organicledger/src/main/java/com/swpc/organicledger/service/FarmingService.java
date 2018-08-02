package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.Farming;

public interface FarmingService {

	Farming get(String farmingCode) throws Exception;

	List<Farming> getAll(Integer firstResult, Integer maxResult) throws Exception;

	List<Farming> getByFarmingProfileCode(String farmingProfileCode, Integer firstResult, Integer maxResult) throws Exception;
	
	void create(Farming farming) throws Exception;

	Farming update(Farming farming) throws Exception;

	void delete(String farmingCode) throws Exception;

	List<Farming> filter(String searchText, String farmingProfileCode, Integer firstResult, Integer maxResult) throws Exception;
}
