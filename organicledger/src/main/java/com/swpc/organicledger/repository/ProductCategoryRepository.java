package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.ProductCategory;

public interface ProductCategoryRepository {

	ProductCategory find(Long id);

	List<ProductCategory> findAll(Integer startRow, Integer endRow);

	List<ProductCategory> search(String search, Integer startRow, Integer endRow);
	
	void create(ProductCategory productCategory);

	ProductCategory update(ProductCategory productCategory);

	void delete(Long id);

	void delete(ProductCategory productCategory);

}
