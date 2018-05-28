package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.Unit;

public interface UnitRepository {

	Unit find(Long id);

	List<Unit> findAll(Integer startRow, Integer endRow);

	List<Unit> search(String search, Integer startRow, Integer endRow);
	
	void create(Unit unit);

	Unit update(Unit unit);

	void delete(Long id);

	void delete(Unit unit);

}
