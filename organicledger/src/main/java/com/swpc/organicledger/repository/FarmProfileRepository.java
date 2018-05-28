package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.FarmProfile;

public interface FarmProfileRepository {

	FarmProfile find(String farmCode);

	List<FarmProfile> findAll(Integer firstResult, Integer maxResult);

	void create(FarmProfile farmProfile);

	FarmProfile update(FarmProfile farmProfile);

	void delete(String farmCode);
	
	List<FarmProfile> filter(String searchText, Integer firstResult, Integer maxResult);
	
	String codeGenerator();
}
