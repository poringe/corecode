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
import com.swpc.organicledger.model.FarmRecord;
import com.swpc.organicledger.repository.FarmRecordRepository;
import com.swpc.organicledger.service.FarmRecordService;
import com.swpc.organicledger.util.Encoders;

@Service
@Import(Encoders.class)
public class FarmRecordServiceImpl implements FarmRecordService {

	@Autowired
	private FarmRecordRepository farmRecordRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_RECORD_READ')")
	public FarmRecord get(Long id) throws Exception {
		try {
			return farmRecordRepository.find(id);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_RECORD_READ')")
	public List<FarmRecord> getAll(Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmRecordRepository.findAll(firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_RECORD_READ')")
	public List<FarmRecord> getByFarmingCode(String farmingCode, Integer firstResult, Integer maxResult) throws Exception {
		try {
			return farmRecordRepository.findByFarmingCode(farmingCode, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('FARM_RECORD_CREATE')")
	public void create(FarmRecord farmRecord) throws Exception {
		try {
			farmRecordRepository.create(farmRecord);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "name");
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
}
