package com.freelapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.freelapp.model.User;
import com.freelapp.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;
	
	@Autowired
	public DatabaseUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
		return new DatabaseUserDetails(user);
	}
}
