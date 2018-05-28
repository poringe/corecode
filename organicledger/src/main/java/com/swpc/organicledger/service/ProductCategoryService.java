package com.swpc.organicledger.service;

import java.util.List;

import com.swpc.organicledger.model.ProductCategory;

public interface ProductCategoryService {

	ProductCategory get(Long id);

	List<ProductCategory> getAll(Integer startRow, Integer endRow);

	List<ProductCategory> search(String search, Integer startRow, Integer endRow);
	
	void create(ProductCategory productCategory);

	ProductCategory update(ProductCategory productCategory);

	void delete(Long id);

	void delete(ProductCategory productCategory);
}
