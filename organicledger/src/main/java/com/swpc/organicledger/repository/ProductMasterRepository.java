package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.ProductMaster;

public interface ProductMasterRepository {

	ProductMaster find(Long id);

	List<ProductMaster> findAll(Integer startRow, Integer endRow);

	List<ProductMaster> search(String search, Integer startRow, Integer endRow);
	
	void create(ProductMaster productMaster);

	ProductMaster update(ProductMaster productMaster);

	void delete(Long id);

	void delete(ProductMaster productMaster);

}
