package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.ProductType;

public interface ProductTypeService {

	ProductType get(Long id);

	List<ProductType> getAll(Integer startRow, Integer endRow);

	List<ProductType> search(String search, Integer startRow, Integer endRow);
	
	void create(ProductType productType);

	ProductType update(ProductType productType);

	void delete(Long id);

	void delete(ProductType productType);
}
