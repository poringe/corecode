package com.swpc.organicledger.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swpc.organicledger.model.User;
import com.swpc.organicledger.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);

		if (user != null) {
			if (!user.isEnabled()) {
				throw new UsernameNotFoundException(username);
			}
			return user;
		}

		throw new UsernameNotFoundException(username);
	}
}
