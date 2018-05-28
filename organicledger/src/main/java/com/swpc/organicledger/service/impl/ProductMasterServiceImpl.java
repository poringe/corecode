package com.swpc.organicledger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.model.ProductMaster;
import com.swpc.organicledger.repository.ProductMasterRepository;
import com.swpc.organicledger.service.ProductMasterService;

@Service
public class ProductMasterServiceImpl implements ProductMasterService {

	@Autowired
	private ProductMasterRepository productMasterRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_READ')")
	public ProductMaster get(Long id) {
		return productMasterRepository.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_READ')")
	public List<ProductMaster> getAll(Integer firstResult, Integer maxResult) {
		return productMasterRepository.findAll(firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_READ')")
	public List<ProductMaster> search(String searchText, Integer firstResult, Integer maxResult) {
		return productMasterRepository.search(searchText, firstResult, maxResult);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_CREATE')")
	public void create(ProductMaster productMaster) {
		productMasterRepository.create(productMaster);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_UPDATE')")
	public ProductMaster update(ProductMaster productMaster) {
		return productMasterRepository.update(productMaster);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_DELETE')")
	public void delete(Long id) {
		productMasterRepository.delete(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('PRODUCT_MASTER_DELETE')")
	public void delete(ProductMaster productMaster) {
		productMasterRepository.delete(productMaster);
	}
}
