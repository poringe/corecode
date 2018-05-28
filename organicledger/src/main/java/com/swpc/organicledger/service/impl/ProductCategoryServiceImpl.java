package com.swpc.organicledger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.model.ProductCategory;
import com.swpc.organicledger.repository.ProductCategoryRepository;
import com.swpc.organicledger.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_READ')")
	public ProductCategory get(Long id) {
		return productCategoryRepository.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_READ')")
	public List<ProductCategory> getAll(Integer firstResult, Integer maxResult) {
		return productCategoryRepository.findAll(firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_READ')")
	public List<ProductCategory> search(String searchText, Integer firstResult, Integer maxResult) {
		return productCategoryRepository.search(searchText, firstResult, maxResult);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_CREATE')")
	public void create(ProductCategory productCategory) {
		productCategoryRepository.create(productCategory);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_UPDATE')")
	public ProductCategory update(ProductCategory productCategory) {
		return productCategoryRepository.update(productCategory);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_DELETE')")
	public void delete(Long id) {
		productCategoryRepository.delete(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_CATEGORY_DELETE')")
	public void delete(ProductCategory productCategory) {
		productCategoryRepository.delete(productCategory);
	}
}
