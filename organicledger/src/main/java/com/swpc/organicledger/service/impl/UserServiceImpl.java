package com.swpc.organicledger.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.exception.OrganicLedgerServiceException;
import com.swpc.organicledger.model.User;
import com.swpc.organicledger.repository.UserRepository;
import com.swpc.organicledger.service.UserService;
import com.swpc.organicledger.util.Encoders;

@Service
@Import(Encoders.class)
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder userPasswordEncoder;

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_READ')")
	public User get(Long id) throws Exception {
		try {
			return userRepository.find(id);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User loadUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_READ')")
	public List<User> getAll(Integer firstResult, Integer maxResult) throws Exception {
		try {
			return userRepository.findAll(firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_CREATE')")
	public void create(User user) throws Exception {
		try {
			user.setPassword(userPasswordEncoder.encode(user.getPassword()));
			userRepository.create(user);
		} catch (DataIntegrityViolationException e) {
			throw new OrganicLedgerServiceException("duplicate_error", "username");
		} catch (Exception e) {
			e.printStackTrace();
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_UPDATE')")
	public User update(User user) throws Exception {
		try {
			if (user.getPassword() != null) {
				user.setPassword(userPasswordEncoder.encode(user.getPassword()));
			} else {
				User temp = userRepository.find(user.getId());
				user.setPassword(temp.getPassword());
			}
			return userRepository.update(user);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_DELETE')")
	public void delete(Long id) throws Exception {
		try {
			userRepository.delete(id);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_DELETE')")
	public void delete(User user) throws Exception {
		try {
			userRepository.delete(user);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}

	@Override
	@Transactional(readOnly = true)
	@PreAuthorize("principal.userType.toString() == 'ADMIN' or hasAuthority('USER_READ')")
	public List<User> filter(String searchText, Integer firstResult, Integer maxResult) throws Exception {
		try {
			return userRepository.filter(searchText, firstResult, maxResult);
		} catch (Exception e) {
			throw new OrganicLedgerServiceException("unknown_error");
		}
	}
}
