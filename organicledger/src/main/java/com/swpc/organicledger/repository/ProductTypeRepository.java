package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.ProductType;

public interface ProductTypeRepository {

	ProductType find(Long id);

	List<ProductType> findAll(Integer startRow, Integer endRow);

	List<ProductType> search(String search, Integer startRow, Integer endRow);
	
	void create(ProductType productType);

	ProductType update(ProductType productType);

	void delete(Long id);

	void delete(ProductType productType);

}
