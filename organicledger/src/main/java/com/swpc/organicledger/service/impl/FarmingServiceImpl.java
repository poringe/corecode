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
import com.swpc.organicledger.model.Farming;
import com.swpc.organicledger.repository.FarmingRepository;
import com.swpc.organicledger.service.FarmingService;
import com.swpc.organicledger.util.Encoders;

@Service
@Import(Encoders.class)
public class FarmingServiceImpl implements FarmingService {

	@Autowired
	private FarmingRepository farmingRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__READ')")
	public Farming get(String farmingCode) throws Exception {
		try {
			return farmingRepository.find(farmingCode);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__READ')")
	public List<Farming> getAll(Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmingRepository.findAll(firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__READ')")
	public List<Farming> getByFarmingProfileCode(String farmProfileCode, Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmingRepository.findByFarmingProfileCode(farmProfileCode, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__CREATE')")
	public void create(Farming farming) throws Exception {
		try {
			farming.setFarmingCode(farmingRepository.codeGenerator(farming.getFarmCode()));
			farming.setOperationType(OperationType.CREATED);
			farming.setEnabled(true);
			farmingRepository.create(farming);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__UPDATE')")
	public Farming update(Farming farming) throws Exception {
		try {
			return farmingRepository.update(farming);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__DELETE')")
	public void delete(String farmingCode) throws Exception {
		try {
			farmingRepository.delete(farmingCode);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARMING__READ')")
	public List<Farming> filter(String searchText, String farmProfileCode,  Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmingRepository.filter(searchText, farmProfileCode, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
}
