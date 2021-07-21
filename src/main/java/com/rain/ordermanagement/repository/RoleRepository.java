package com.rain.ordermanagement.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.rain.ordermanagement.model.Role;
import com.rain.ordermanagement.model.RoleType;

public interface RoleRepository extends CrudRepository<Role, Long> {

	Optional<Role> findByType(RoleType roleUser);

}
