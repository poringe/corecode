package com.swpc.organicledger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.model.Unit;
import com.swpc.organicledger.repository.UnitRepository;
import com.swpc.organicledger.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {

	@Autowired
	private UnitRepository unitRepository;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_READ')")
	public Unit get(Long id) {
		return unitRepository.find(id);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_READ')")
	public List<Unit> getAll(Integer firstResult, Integer maxResult) {
		return unitRepository.findAll(firstResult, maxResult);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_READ')")
	public List<Unit> search(String searchText, Integer firstResult, Integer maxResult) {
		return unitRepository.search(searchText, firstResult, maxResult);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_CREATE')")
	public void create(Unit unit) {
		unitRepository.create(unit);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_UPDATE')")
	public Unit update(Unit unit) {
		return unitRepository.update(unit);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_DELETE')")
	public void delete(Long id) {
		unitRepository.delete(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('UNIT_DELETE')")
	public void delete(Unit unit) {
		unitRepository.delete(unit);
	}
}
