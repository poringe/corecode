package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.FarmRecord;

public interface FarmRecordService {

	FarmRecord get(Long id) throws Exception;

	List<FarmRecord> getAll(Integer firstResult, Integer maxResult) throws Exception;

	List<FarmRecord> getByFarmingCode(String farmingCode, Integer firstResult, Integer maxResult) throws Exception;
	
	void create(FarmRecord farmRecord) throws Exception;
}
