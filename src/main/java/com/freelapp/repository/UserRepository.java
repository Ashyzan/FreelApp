package com.freelapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.freelapp.model.User;
import com.freelapp.restModel.RestTask;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByEmail(String email);
	
}
