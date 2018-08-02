package com.swpc.organicledger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.emodel.OperationType;
import com.swpc.organicledger.exception.OrganicLedgerServiceException;
import com.swpc.organicledger.model.Farm;
import com.swpc.organicledger.repository.FarmRepository;
import com.swpc.organicledger.service.FarmService;
import com.swpc.organicledger.util.Encoders;

@Service
@Import(Encoders.class)
public class FarmServiceImpl implements FarmService {

	@Autowired
	private FarmRepository farmRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__READ')")
	public Farm get(String farmCode) throws Exception {
		try {
			return farmRepository.find(farmCode);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__READ')")
	public List<Farm> getAll(Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmRepository.findAll(firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__READ')")
	public List<Farm> getByFarmProfileCode(String farmProfileCode, Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmRepository.findByFarmProfileCode(farmProfileCode, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__CREATE')")
	public void create(Farm farm) throws Exception {
		try {
			farm.setFarmCode(farmRepository.codeGenerator(farm.getFarmProfileCode()));
			farm.setOperationType(OperationType.CREATED);
			farm.setEnabled(true);
			farmRepository.create(farm);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__UPDATE')")
	public Farm update(Farm farm) throws Exception {
		try {
			return farmRepository.update(farm);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__DELETE')")
	public void delete(String farmCode) throws Exception {
		try {
			farmRepository.delete(farmCode);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM__READ')")
	public List<Farm> filter(String searchText, String farmProfileCode,  Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmRepository.filter(searchText, farmProfileCode, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
}
