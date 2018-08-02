package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.Farm;

public interface FarmService {

	Farm get(String farmCode) throws Exception;

	List<Farm> getAll(Integer firstResult, Integer maxResult) throws Exception;

	List<Farm> getByFarmProfileCode(String farmProfileCode, Integer firstResult, Integer maxResult) throws Exception;
	
	void create(Farm farm) throws Exception;

	Farm update(Farm farm) throws Exception;

	void delete(String farmCode) throws Exception;

	List<Farm> filter(String searchText, String farmProfileCode, Integer firstResult, Integer maxResult) throws Exception;
}
