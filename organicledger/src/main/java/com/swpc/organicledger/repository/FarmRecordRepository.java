package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.FarmRecord;

public interface FarmRecordRepository {

	FarmRecord find(Long id);

	List<FarmRecord> findAll(Integer firstResult, Integer maxResult);

	List<FarmRecord> findByFarmingCode(String farmingCode, Integer firstResult, Integer maxResult);
	
	void create(FarmRecord farmRecord);
}
