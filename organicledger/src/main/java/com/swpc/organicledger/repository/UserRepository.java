package com.swpc.organicledger.repository;

import java.util.List;

import com.swpc.organicledger.model.User;

public interface UserRepository {

	User find(Long id);

	List<User> findAll(Integer firstResult, Integer maxResult);

	void create(User user);

	User update(User user);

	void delete(Long id);

	void delete(User user);

	User findByUsername(String username);
	
	List<User> filter(String searchText, Integer firstResult, Integer maxResult);
}
