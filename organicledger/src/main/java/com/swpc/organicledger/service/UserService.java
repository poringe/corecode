package com.swpc.organicledger.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.swpc.organicledger.model.User;

public interface UserService {

	User get(Long id) throws Exception;

	User loadUserByUsername(String username) throws UsernameNotFoundException;

	List<User> getAll(Integer firstResult, Integer maxResult) throws Exception;

	void create(User company) throws Exception;

	User update(User company) throws Exception;

	void delete(Long id) throws Exception;

	void delete(User company) throws Exception;

	List<User> filter(String searchText, Integer firstResult, Integer maxResult) throws Exception;
}
