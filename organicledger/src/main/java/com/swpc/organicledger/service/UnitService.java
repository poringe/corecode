package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.Unit;

public interface UnitService {

	Unit get(Long id);

	List<Unit> getAll(Integer startRow, Integer endRow);

	List<Unit> search(String search, Integer startRow, Integer endRow);
	
	void create(Unit unit);

	Unit update(Unit unit);

	void delete(Long id);

	void delete(Unit unit);
}
