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
import com.swpc.organicledger.model.FarmProfile;
import com.swpc.organicledger.repository.FarmProfileRepository;
import com.swpc.organicledger.service.FarmProfileService;
import com.swpc.organicledger.util.Encoders;

@Service
@Import(Encoders.class)
public class FarmProfileServiceImpl implements FarmProfileService {

	@Autowired
	private FarmProfileRepository farmProfileRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_PROFILE_READ')")
	public FarmProfile get(String farmCode) throws Exception {
		try {
			return farmProfileRepository.find(farmCode);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_PROFILE_READ')")
	public List<FarmProfile> getAll(Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmProfileRepository.findAll(firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_PROFILE_CREATE')")
	public void create(FarmProfile farmProfile) throws Exception {
		try {
			farmProfile.setFarmProfileCode(farmProfileRepository.codeGenerator());
			farmProfile.setOperationType(OperationType.CREATED);
			farmProfile.setEnabled(true);
			farmProfileRepository.create(farmProfile);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_PROFILE_UPDATE')")
	public FarmProfile update(FarmProfile farmProfile) throws Exception {
		try {
			return farmProfileRepository.update(farmProfile);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_PROFILE_DELETE')")
	public void delete(String farmCode) throws Exception {
		try {
			farmProfileRepository.delete(farmCode);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_PROFILE_READ')")
	public List<FarmProfile> filter(String searchText, Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmProfileRepository.filter(searchText, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
}
