package com.rain.ordermanagement.repository;

import org.springframework.data.repository.CrudRepository;

import com.rain.ordermanagement.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	boolean existsByUsername(String username);

}
