package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.ProductMaster;

public interface ProductMasterService {

	ProductMaster get(Long id);

	List<ProductMaster> getAll(Integer startRow, Integer endRow);

	List<ProductMaster> search(String search, Integer startRow, Integer endRow);
	
	void create(ProductMaster productMaster);

	ProductMaster update(ProductMaster productMaster);

	void delete(Long id);

	void delete(ProductMaster productMaster);
}
