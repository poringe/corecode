package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.FarmProfile;

public interface FarmProfileService {

	FarmProfile get(String farmCode) throws Exception;

	List<FarmProfile> getAll(Integer firstResult, Integer maxResult) throws Exception;

	void create(FarmProfile farmProfile) throws Exception;

	FarmProfile update(FarmProfile farmProfile) throws Exception;

	void delete(String farmCode) throws Exception;

	List<FarmProfile> filter(String searchText, Integer firstResult, Integer maxResult) throws Exception;
}
