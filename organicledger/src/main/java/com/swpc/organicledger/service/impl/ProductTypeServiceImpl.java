package com.swpc.organicledger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.model.ProductType;
import com.swpc.organicledger.repository.ProductTypeRepository;
import com.swpc.organicledger.service.ProductTypeService;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

	@Autowired
	private ProductTypeRepository productTypeRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_READ')")
	public ProductType get(Long id) {
		return productTypeRepository.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_READ')")
	public List<ProductType> getAll(Integer firstResult, Integer maxResult) {
		return productTypeRepository.findAll(firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_READ')")
	public List<ProductType> search(String searchText, Integer firstResult, Integer maxResult) {
		return productTypeRepository.search(searchText, firstResult, maxResult);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_CREATE')")
	public void create(ProductType productType) {
		productTypeRepository.create(productType);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_UPDATE')")
	public ProductType update(ProductType productType) {
		return productTypeRepository.update(productType);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_DELETE')")
	public void delete(Long id) {
		productTypeRepository.delete(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_TYPE_DELETE')")
	public void delete(ProductType productType) {
		productTypeRepository.delete(productType);
	}
}
